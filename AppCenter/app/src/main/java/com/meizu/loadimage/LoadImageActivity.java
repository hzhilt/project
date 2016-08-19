package com.meizu.loadimage;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.appcenter.R;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 14-11-28.
 */
public class LoadImageActivity extends Activity {

    private ListView mListView;
    private List<Icon> mIcons;

    static final int MESSAGE_ICON = 1;
    static final int MESSAGE_UPDATE = 2;
    static final int MESSAGE_ERROR = 3;
    static final int MESSAGE_NOTIFYLIST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadimage_main);
        mListView = (ListView) findViewById(R.id.iconList);

        String url = "http://api-app.meizu.com/apps/public/mime/recommend?os=18";
        loadData(url);
    }

    private Handler mHandlerImage = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_ICON:
                    mIcons = (List<Icon>) msg.obj;
                    mListView.setAdapter(myAdapter);
                    break;
                case MESSAGE_UPDATE:
                    Image icon = (Image) msg.obj;
                    icon.imageView.setImageDrawable(icon.drawable);
                    break;
                case MESSAGE_ERROR:
                    Toast.makeText(LoadImageActivity.this, "can not connect to the server", Toast.LENGTH_LONG).show();
                    break;
                case MESSAGE_NOTIFYLIST:
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void loadData(final String url) {

        final LoadDataFromNet loadData = new LoadDataFromNet(url, null);
        Thread iconThread = new Thread() {
            @Override
            public void run() {
                String string = loadData.getResult();
                List<Icon> icons = new ArrayList<Icon>();
                try {
                    JsonData jsonData = new JsonData(string, "value", "data");
                    icons = jsonData.getData();
                    Log.i("here", "get json");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Message message = new Message();
                message.what = MESSAGE_ICON;
                message.obj = icons;
                mHandlerImage.sendMessage(message);
            }
        };
        iconThread.start();
    }

    private BaseAdapter myAdapter = new BaseAdapter() {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.i("here", "getView:" + position);
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(LoadImageActivity.this).inflate(R.layout.loadimage_item, null);
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageIcon);
                holder.textView = (TextView) convertView.findViewById(R.id.imageName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String url = mIcons.get(position).url;
            String name = mIcons.get(position).name;
            holder.textView.setText(name);


            LoadImage loadImage = LoadImage.getInstance();
            loadImage.getImage(holder, url, mHandlerImage, position);

            return convertView;

        }
    };

    class Image {
        Drawable drawable;
        ImageView imageView;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

    @Override
    protected void onDestroy() {
        //android.os.Process.killProcess(android.os.Process.myTid());
        super.onDestroy();
    }


}

