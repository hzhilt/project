package com.meizu.loadimage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 14-12-2.
 *
 * 本地图片工具类，包括了网络图片的缓存在本地，查找，本地图片深度查找
 *
 */
public class NativeImage {

    private List<Icon> mIcons;
    private File mFile;
    private List<File> mImageFileList = new ArrayList<>();
    private int DEPTH_TIMES;
    private String url;
    private Bitmap bitmap;

    private Context mContext;

    public NativeImage(String url, Bitmap bitmap,Context context) {
        this.url = url;
        this.bitmap = bitmap;
        this.mContext = context;
    }

    public NativeImage(File File,int depth) {
        this.mFile = File;
        this.DEPTH_TIMES = depth;
    }
    public NativeImage(File File,Context context) {
        this.mFile = File;
        this.mContext = context;
    }

    public NativeImage(File file){
        this.mFile = file;
    }

    public void SaveImage(){
        File dir = new File("/mnt/sdcard/test/");
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        File bitmapFile = new File("/mnt/sdcard/test/" +
                url.substring(url.lastIndexOf("/") + 1));
        if(!bitmapFile.exists())
        {
            try
            {
                bitmapFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        FileOutputStream fos;
        try
        {
            fos = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,
                    100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmap(String url){
        Bitmap bitmap = null;
        String bitmapName = url.substring(url.lastIndexOf("/") + 1);
        File cacheDir = new File("/mnt/sdcard/test/");
        File[] cacheFiles = cacheDir.listFiles();
        int i = 0;
        for(; i<cacheFiles.length; i++)
        {
            if(bitmapName.equals(cacheFiles[i].getName()))
            {
                break;
            }
        }

        if(i < cacheFiles.length)
        {
            bitmap =  BitmapFactory.decodeFile("/mnt/sdcard/test/" + bitmapName);
        }
        return bitmap;
    }

    public List<Icon> getmIcons(){

        List<File> list = getFile();
        for (int i = 0; i < list.size(); i++) {
            Icon icon = new Icon();
            icon.url = list.get(i).toString();
            icon.name = icon.url.substring(icon.url.lastIndexOf("/") + 1, icon.url.lastIndexOf("."));
            icon.size = list.get(i).length()/1024;
            mIcons.add(icon);
        }
        return mIcons;
    }

    public List<String> getUrlListFromFile(){
        File[] fileArray =mFile.listFiles();

        for (File f:fileArray){
            if(f.isFile()){
                if(("jpg".equalsIgnoreCase(getFileEx(f)))
                        ||("png".equalsIgnoreCase(getFileEx(f)))
                        ||("jpeg".equalsIgnoreCase(getFileEx(f))))
                {
                    mImageFileList.add(f);
                }
            }else{

                    getUrlListFromFile();
            }
        }

        try {
            for (File f : fileArray) {
                if(f.isFile()){
                    if(("jpg".equalsIgnoreCase(getFileEx(f)))
                            ||("png".equalsIgnoreCase(getFileEx(f)))
                            ||("jpeg".equalsIgnoreCase(getFileEx(f))))
                    {
                        mImageFileList.add(f);
                    }
                }else{
                    if(getDepth(f) <= DEPTH_TIMES)
                        getUrlListFromFile();
                }
            }
        }catch (Exception ex){
        }

        List<String> urlList = new ArrayList<>();
        for (File f: mImageFileList){
            urlList.add(f.getAbsolutePath());
        }
        return urlList;
    }



    //递归实现
    public List<File> getFile(){
        File[] fileArray =mFile.listFiles();

        try {
            for (File f : fileArray) {
                if(f.isFile()){
                    if(("jpg".equalsIgnoreCase(getFileEx(f)))
                            ||("png".equalsIgnoreCase(getFileEx(f)))
                            ||("jpeg".equalsIgnoreCase(getFileEx(f))))
                    {

                        mImageFileList.add(f);
                    }
                }else{
                    if(getDepth(f) <= DEPTH_TIMES)
                        getFile();
                }
            }
        }catch (Exception ex){
        }
        return mImageFileList;
    }

    public List<String> getImageUrl(){
        LinkedList<File> fileList =new LinkedList<>();
        if(mFile.isDirectory()){
            fileList.addLast(mFile);
        }
        while (fileList.size() > 0){
            File file = fileList.removeFirst();
            for (File f:file.listFiles()){
                if(f.isFile()){
                    if(("jpg".equalsIgnoreCase(getFileEx(f)))
                            ||("png".equalsIgnoreCase(getFileEx(f)))
                            ||("jpeg".equalsIgnoreCase(getFileEx(f))))
                    {
                        mImageFileList.add(f);
                    }
                }else if(f.isDirectory()){
                    fileList.addLast(f);
                }
            }
        }

        List<String> urlList = new ArrayList<>();
        for (File f: mImageFileList){
            urlList.add(f.getAbsolutePath());
        }
        return urlList;

    }

    public List<String> getUrlListFromStore(){
        List<String> urlList = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            urlList.add(data);
            cursor.moveToNext();
        }

        cursor.close();
        return urlList;
    }

    public String getFileEx(File file){
        String fileName=file.getName();
        String str=fileName.substring(fileName.lastIndexOf(".")+1);
        return str;
    }

    public int getDepth(File file){
        int t=0;
        String path = file.getAbsolutePath();
        for (int i = 0;i < path.length();i++ )
        {
            String b = path.substring(i,i+1);
            if ("/".equals(b))
            {
                t++;
            }
        }
        return t;
    }


}
