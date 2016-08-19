package com.meizu.flyme.calendar.subcription_new.recommend.cards.classify;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subscription.ImageLoaderUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by huangzhihao on 16-8-8.
 */
public class ClassifyGridViewAdapter extends BaseAdapter {

    Context mContext;
    List<Classify> mDatas;

    public ClassifyGridViewAdapter(Context context,List<Classify> datas) {
        mContext = context;
        mDatas = datas;

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.subscribe_new_classify_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.classify_item_icon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.classify_item_title);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ImageLoaderUtils.displayImage(mDatas.get(position).getIcon(), viewHolder.imageView, R.drawable.ic_account_editor, null);
        viewHolder.textView.setText(mDatas.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDatas.get(position).getImg()));
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
