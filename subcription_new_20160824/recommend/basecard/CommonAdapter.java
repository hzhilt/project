package com.meizu.flyme.calendar.subcription_new.recommend.basecard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meizu.flyme.calendar.subcription_new.classify.ClassifyContract;

import java.util.List;

import flyme.support.v7.widget.MzRecyclerView;

/**
 * Created by huangzhihao on 16-8-11.
 */
public abstract class CommonAdapter<T> extends MzRecyclerView.Adapter<ViewHolder>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;


    public CommonAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
}
