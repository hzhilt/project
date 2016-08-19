package com.meizu.loadimage;

import android.graphics.drawable.Drawable;
import android.os.Handler;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 14-12-2.
 * 网上的一个异步加载网络图片工具类
 */
public class AsyncImageLoader {

    //create cache
    public Map<String, SoftReference<Drawable>> mImageCache = new HashMap<String, SoftReference<Drawable>>();

    private ExecutorService mExecutorService = Executors.newFixedThreadPool(5); // 固定五个线程来执行任务
    private final Handler handler = new Handler();

    public Drawable loadDrawable(final String imageUrl,
                                 final ImageCallback callback) {
        // get data from cache
        if (mImageCache.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = mImageCache.get(imageUrl);
            if (softReference.get() != null) {
                return softReference.get();
            }
        }
        // get data from web,then put in cache
        mExecutorService.submit(new Runnable() {
            public void run() {
                try {
                    final Drawable drawable = loadImageFromUrl(imageUrl);

                    mImageCache.put(imageUrl, new SoftReference<Drawable>(
                            drawable));

                    handler.post(new Runnable() {
                        public void run() {
                            callback.imageLoaded(drawable);
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return null;
    }

    // get data
    protected Drawable loadImageFromUrl(String imageUrl) {
        try {
            return Drawable.createFromStream(new URL(imageUrl).openStream(),
                    "image.png");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 对外界开放的回调接口
    public interface ImageCallback {
        // 注意 此方法是用来设置目标对象的图像资源
        public void imageLoaded(Drawable imageDrawable);
    }
}

/*            if (!mIcons.get(position).state) {
                mIcons.get(position).state = true;
                AsyncImageLoader asyncImageLoader3 = new AsyncImageLoader();

                Drawable cacheImage = asyncImageLoader3.loadDrawable(mIcons.get(position).url,
                        new AsyncImageLoader.ImageCallback() {
                            // 请参见实现：如果第一次加载url时下面方法会执行
                            public void imageLoaded(Drawable imageDrawable) {
                                holder.imageView.setImageDrawable(imageDrawable);
                            }
                        });
                if (cacheImage != null) {
                    holder.imageView.setImageDrawable(cacheImage);
                }
            }*/