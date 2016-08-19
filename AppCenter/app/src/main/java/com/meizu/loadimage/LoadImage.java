package com.meizu.loadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.meizu.appcenter.R;

import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 14-12-5.
 */
public class LoadImage {

    private static int CORES_NUM = Runtime.getRuntime().availableProcessors();
    public ExecutorService mExecutorService = Executors.newFixedThreadPool(CORES_NUM);
    private ImageFromSD mImageFromSD = new ImageFromSD();

    //singleton
    private static LoadImage mUniqueInstance = null;
    private LoadImage() {
    }
    public static LoadImage getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new LoadImage();
        }
        return mUniqueInstance;
    }
    public Map<ImageView,Integer> mCondition = new WeakHashMap<>();

    int mCacheSize = 10 * 1024 * 1024;
    private LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(mCacheSize){

        //必须重写此方法，来测量Bitmap的大小
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }
    };

    public void getImageThumbnail(final String imagePath,
                                  final ImageView imageView,final Handler handler,
                                  final int width, final int height,final int position) {

        mCondition.put(imageView, position);

        if (mImageCache.get(imagePath) != null) {
            imageView.setImageBitmap(mImageCache.get(imagePath));
        } else {
            imageView.setImageResource(R.drawable.ic_launcher);
            Thread thread = new Thread() {
                @Override
                public void run() {

                    final Bitmap bitmap = getTumb(imagePath, width, height);

                    synchronized (this) {
                        if (mImageCache.get(imagePath) == null && bitmap != null) {
                            mImageCache.put(imagePath, bitmap);
                        }
                    }
                    if (mCondition.get(imageView) == position) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            };
            mExecutorService.submit(thread);
        }
    }

    public Bitmap getTumb(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(imagePath, options);

        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFile(imagePath, options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public void getImage(final LoadImageActivity.ViewHolder viewHolder,
                         final String url, final Handler handler,
                         final int position) {

        if (mImageCache.get(url) != null) {
            viewHolder.imageView.setImageBitmap(mImageCache.get(url));
        } else {

            Bitmap bitmap = mImageFromSD.findImageFromSD(url);
            if (null != bitmap) {
                viewHolder.imageView.setImageBitmap(bitmap);
            }
            else{
                viewHolder.imageView.setImageResource(R.drawable.ic_launcher);

                Thread thread = new Thread() {
                    Bitmap bitmap;
                    @Override
                    public void run() {
                        {
                            mCondition.put(viewHolder.imageView, position);
                            try {
                                LoadDataFromNet load = new LoadDataFromNet(null, url);
                                synchronized (this) {
                                    bitmap = load.loadImageFormNet();

                                    if (mImageCache.get(url) == null && bitmap != null) {
                                        mImageCache.put(url, bitmap);
                                    }

                                    mImageFromSD.saveBmpToSd(bitmap, url);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (mCondition.get(viewHolder.imageView) == position) {
                                handler.post(new Runnable() {
                                @Override
                                public void run() {
                                        viewHolder.imageView.setImageBitmap(bitmap);
                                    }
                            });
                            }
                        }
                    }
                };
                mExecutorService.submit(thread);
            }
        }
    }

    private void stopThread(String target) {
        int n = Thread.activeCount();

        Thread[] threads = new Thread[n];
        Thread.enumerate(threads);
        for (Thread thread : threads) {
            Log.i("there", "Thread :" + thread.getName());
            if (thread.getName().equals(target)) {
                thread.interrupt();
                Log.i("there", "cancel Thread :" + thread.getName());
                break;
            }
        }
        Log.i("there", "----------");
    }


    public Bitmap getSpecifySize(Bitmap bitmap, int lastWidth, int lastHeight) {
        return null;
    }

    public Bitmap getSmallSize(String url, int mThumbnailWidth, int mThumbnailHeight) {
        return null;
    }
}
