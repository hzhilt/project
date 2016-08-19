package com.meizu.volleyPicassoTest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.meizu.appcenter.R;
import com.meizu.loadimage.Icon;
import com.meizu.loadimage.JsonData;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-12-9.
 */
public class VolleyImageActivity extends Activity {

    private ListView mListView;
    private RequestQueue mRequestQueue;
    private RequestQueue imageQueue;
    private List<Icon> mIcons;
    private VolleyAdapter mVA;

    static final String JSON_URL = "http://api-app.meizu.com/apps/public/mime/recommend?os=18";

    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;


        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }

    public MyImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadimage_main);

        mListView = (ListView) findViewById(R.id.iconList);
        mIcons = new ArrayList<Icon>();

        mRequestQueue = Volley.newRequestQueue(this);
        imageQueue = new Volley().newRequestQueue(this);
        imageLoader = new MyImageLoader(imageQueue, new BitmapCache());

        mVA = new VolleyAdapter(this);
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JsonData jsonData = new JsonData("value", "data");
                    mIcons = jsonData.getJsonData(response);

                    mListView.setAdapter(mVA);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(jr);


    }

    private class VolleyAdapter extends BaseAdapter {

        private Context context;

        VolleyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mIcons.size();
        }

        @Override
        public Object getItem(int position) {
            return mIcons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(VolleyImageActivity.this).inflate(R.layout.loadimage_item, null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageIcon);
                holder.textView = (TextView) convertView.findViewById(R.id.imageName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String url = mIcons.get(position).url;
            String name = mIcons.get(position).name;
            holder.textView.setText(name);
            holder.imageView.setTag(url);

            // use picasso
            Picasso.with(context).load(url).into(holder.imageView);

            //use volley
//            MyImageLoader.ImageListener listener = MyImageLoader.getImageListener(holder.imageView, url,
//                    R.drawable.ic_launcher, R.drawable.downloada);
//
//            imageLoader.get(url, listener);

            return convertView;
        }

        class ViewHolder {
            TextView textView;
            ImageView imageView;
        }

    }

}
