package com.meizu.loadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by root on 14-12-8.
 */
public class ImageFromSD {

    private final static String IMAGE_DIR = "/myImage/";

    public void saveBmpToSd(Bitmap bm, String url) {

        String filename = convertUrlToFileName(url);
        String dir = getDirectory();
        File file = new File(dir, filename);

        try {
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDirectory() {

        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString(); // 取得SD根路径
        String dirPath = extStorageDirectory + IMAGE_DIR;
        File dirFile = new File(dirPath);

        if (!dirFile.exists()) {
            //dirFile.mkdirs();
            return null;
        }
        return dirPath;
    }

    private String convertUrlToFileName(String url) {
        String filename = url;
        filename = filename.replace("http://", "");
        filename = filename.replace("/", ".");
        filename = filename.replace(":", ".");
        return filename;
    }

    public Bitmap findImageFromSD(String url) {

        String fileName = convertUrlToFileName(url);
        String filepath = getDirectory();
        String pathFileName = filepath + fileName;
        File pathFile = new File(pathFileName);

        if (!pathFile.exists()) {
//            try {
//                //pathFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return null;
        }

        Bitmap bmp = BitmapFactory.decodeFile(pathFile.getPath());
        Log.i("here", pathFile.getPath());
        return bmp;
    }
}
