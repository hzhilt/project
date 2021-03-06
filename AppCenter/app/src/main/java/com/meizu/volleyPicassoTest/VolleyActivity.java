package com.meizu.volleyPicassoTest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.meizu.appcenter.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 14-12-5.
 */
public class VolleyActivity extends Activity {

    private String TAG = this.getClass().getSimpleName();
    private ListView mListView;
    private RequestQueue mRequestQueue;
    private LayoutInflater mLF;

    private ArrayList<NewsModel> mNews ;
    private VolleyAdapter mVA;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_main);

        mListView = (ListView)findViewById(R.id.volleyList);
        mLF = LayoutInflater.from(this);

        mNews = new ArrayList<NewsModel>();
        mVA = new VolleyAdapter();

        mListView.setAdapter(mVA);

        mRequestQueue =  Volley.newRequestQueue(this);
        String url = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";
        pd = ProgressDialog.show(this,"Please Wait...","Please Wait...");
        try{
            Thread.sleep(2000);
        }catch(Exception e){

        }
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, response.toString());
                parseJSON(response);
                mVA.notifyDataSetChanged();
                pd.dismiss();
                }
            },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,error.getMessage());
            }
        });
        mRequestQueue.add(jr);

    }

    private void parseJSON(JSONObject json) {
        try{
            JSONObject value = json.getJSONObject("value");
            JSONArray items = value.getJSONArray("items");
            for(int i=0;i<items.length();i++){

                JSONObject item = items.getJSONObject(i);
                NewsModel nm = new NewsModel();
                nm.setTitle(item.optString("title"));
                nm.setDescription(item.optString("description"));
                nm.setLink(item.optString("link"));
                nm.setPubDate(item.optString("pubDate"));
                mNews.add(nm);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    class NewsModel{
        private String title;
        private String link;
        private String description;
        private String pubDate;

        void setTitle(String title) {
            this.title = title;
        }

        void setLink(String link) {
            this.link = link;
        }

        void setDescription(String description) {
            this.description = description;
        }

        void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        String getLink() {
            return link;
        }

        String getDescription() {
            return description;
        }

        String getPubDate() {
            return pubDate;
        }

        String getTitle() {

            return title;
        }
    }

    private class VolleyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mNews.size();
        }

        @Override
        public Object getItem(int position) {
            return mNews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder vh ;
            if(view == null){
                vh = new ViewHolder();
                view = mLF.inflate(R.layout.volley_item,null);
                vh.tvTitle = (TextView) view.findViewById(R.id.txtTitle);
                vh.tvDesc = (TextView) view.findViewById(R.id.txtDesc);
                vh.tvDate = (TextView) view.findViewById(R.id.txtDate);
                view.setTag(vh);
            }
            else{
                vh = (ViewHolder) view.getTag();
            }

            NewsModel nm = mNews.get(position);
            vh.tvTitle.setText(nm.getTitle());
            vh.tvDesc.setText(nm.getDescription());
            vh.tvDate.setText(nm.getPubDate());
            return view;
        }

        class  ViewHolder{
            TextView tvTitle;
            TextView tvDesc;
            TextView tvDate;

        }

    }

    //AsyncTask
}
