package com.meizu.photos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.meizu.appcenter.R;
import com.meizu.appcenter.ViewHolder;
import com.meizu.loadimage.LoadImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-12-15.
 */
public class ThumbAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> mUrlList = new ArrayList<>();
    private Handler mHandler;

    public ThumbAdapter(Context context,List<String> urlList,Handler handler) {
        this.mContext = context.getApplicationContext();
        this.mUrlList = urlList;
        this.mHandler = handler;
    }

    @Override
    public int getCount() {
        return mUrlList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUrlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photos_item, null);
            imageView = (ImageView) convertView.findViewById(R.id.thumb);
            convertView.setTag(imageView);
        }
        else {
            imageView = (ImageView)convertView.getTag();
        }

        String url = mUrlList.get(position);
        LoadImage loadImage = LoadImage.getInstance();
        loadImage.getImageThumbnail(url,imageView,mHandler,90,90,position);

        Log.i("here",convertView.toString()+"======"+position);
        return convertView;
    }

}
