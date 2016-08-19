package com.meizu.listviewload;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.meizu.appcenter.R;
import com.meizu.gridview.Data;

import java.util.List;

/**
 * Created by root on 14-11-13.
 */
public class ColorAdapter extends BaseAdapter {

    private Context mContext;
    private List<Data> mData;

    public ColorAdapter(Context context,List<Data> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size()%3 == 0 ? mData.size()/3 : mData.size()/3 +1;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position * 3);
    }

    @Override
    public long getItemId(int position) {
        return position * 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderColor viewHolderColor = null;
        if (convertView == null)
        {
            viewHolderColor = new ViewHolderColor();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listviewload_item,null);
            viewHolderColor.imageViewL = (ImageView)convertView.findViewById(R.id.imageViewL);
            viewHolderColor.imageViewM = (ImageView)convertView.findViewById(R.id.imageViewM);
            viewHolderColor.imageViewR = (ImageView)convertView.findViewById(R.id.imageViewR);
            convertView.setTag(viewHolderColor);
        }
        else {
            viewHolderColor = (ViewHolderColor)convertView.getTag();
        }

        viewHolderColor.imageViewL.setBackgroundColor(Color.parseColor(mData.get(3 * position).mColorID));
        if(3 * position + 1 >= mData.size()) {
            viewHolderColor.imageViewM.setVisibility(View.INVISIBLE);
        }
        else {
            viewHolderColor.imageViewM.setVisibility(View.VISIBLE);
            viewHolderColor.imageViewM.setBackgroundColor(Color.parseColor(mData.get(3 * position + 1).mColorID));
        }

        if(3 * position + 2 >= mData.size())
            viewHolderColor.imageViewR.setVisibility(View.INVISIBLE);
        else {
            viewHolderColor.imageViewR.setVisibility(View.VISIBLE);
            viewHolderColor.imageViewR.setBackgroundColor(Color.parseColor(mData.get(3 * position + 2).mColorID));
        }


        return convertView;
    }

    class ViewHolderColor{
        ImageView imageViewL;
        ImageView imageViewM;
        ImageView imageViewR;
    }
}
