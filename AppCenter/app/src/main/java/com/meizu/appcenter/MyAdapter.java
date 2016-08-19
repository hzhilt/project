package com.meizu.appcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.meizu.app.data.BaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-11-5.
 */
public class MyAdapter extends BaseAdapter {

    public Context mContext;
    public LayoutInflater mInflater;
    public List<BaseData> mBaseData = new ArrayList<BaseData>();
    final int VIEW_TYPE = 5;

    public MyAdapter(Context context,List<BaseData> baseData) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mBaseData = baseData;

    }
    @Override
    public int getCount() {
        return mBaseData.size();
    }
    @Override
    public Object getItem(int position) {
        return mBaseData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder(type);
            viewHolder = viewHolder.creatInstance(type);
            convertView = viewHolder.creatView(mContext);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        convertView = viewHolder.updateView(mBaseData.get(position));

        return convertView;

    }

    @Override
    public int getItemViewType(int position) {
        return mBaseData.get(position).VIEW_TYPE;
    }
    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }
}
