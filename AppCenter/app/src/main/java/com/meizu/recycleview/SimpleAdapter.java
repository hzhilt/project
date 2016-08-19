package com.meizu.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.appcenter.R;

import java.util.List;

/**
 * Created by root on 15-1-4.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
    //数据集
    private List<Integer> mData;
    private Context mContext;

    //private AdapterView.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public SimpleAdapter(List<Integer> dataset, Context context) {
        //super();
        mData = dataset;
        mContext = context;
    }

    //定义ViewHolder，包括两个控件
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.recycleview_item, null);
        // 创建ViewHolder
        ViewHolder holder = new ViewHolder(view);
        holder.mImageView = (ImageView)view.findViewById(R.id.id_index_item_image);
        holder.mTextView = (TextView)view.findViewById(R.id.id_index_item_text);
        return holder;
    }

    //返回数据的长度
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        //设置TextView内容
        viewHolder.mTextView.setText("pic");
        //设置ImageView资源
        viewHolder.mImageView.setImageResource(mData.get(i));

        if (mOnItemClickLitener != null)
        {
            viewHolder.mImageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(viewHolder.mImageView, i);
                }
            });

        }
    }
}
