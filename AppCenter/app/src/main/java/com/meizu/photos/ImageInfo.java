package com.meizu.photos;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.meizu.loadimage.NativeImage;

import java.io.File;
import java.util.List;

/**
 * Created by root on 14-12-15.
 */
public class ImageInfo {

    private Context mContext;
    private Handler mHandler;

    ImageInfo(Context context,Handler handler){
        this.mContext = context;
        this.mHandler = handler;
    }

    public ImageInfo(Context context){
        this.mContext = context;
    }
    private String mExtStorageDirectory = Environment.getExternalStorageDirectory()
            .toString(); // 取得SD根路径
    private String mDirPath = mExtStorageDirectory;
    private File mDirFile = new File(mDirPath);

    public List<String> getUrlList() {

        List<String> urlList;
        //use media.storage
        NativeImage nativeImage = new NativeImage(mDirFile,mContext);
        urlList = nativeImage.getUrlListFromStore();

        //use recursion
//        NativeImage nativeImage = new NativeImage(mDirFile,4);
//        urlList = nativeImage.getUrlListFromFile();

        //use for need async to get the
        return urlList;
    }

    public void getUrl(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                NativeImage nativeImage = new NativeImage(mDirFile);
                List<String> urlList = nativeImage.getImageUrl();

                Message message = new Message();
                message.what = 1;
                message.obj = urlList;
                mHandler.sendMessage(message);

            }
        };
        thread.start();
    }
}
