package com.meizu.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * Created by root on 14-11-10.
 */
public class MyAdapter extends BaseAdapter{

    private Context mContext;
    private List<Data> mData;
    public boolean isMul;

    public MyAdapter(Context context,List<Data> data,boolean status) {
        mContext = context;
        mData = data;
        isMul = status;
    }

    public void setData(List<Data> mData) {
        this.mData = mData;
    }

    public void setMul(boolean isMul) {
        this.isMul = isMul;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderGrid viewHolderGrid = null;

        if(convertView == null)
        {
            viewHolderGrid = new ViewHolderGrid();
            convertView = viewHolderGrid.creatView(mContext);
            convertView.setTag(viewHolderGrid);
        }
        else{
            //
            viewHolderGrid = (ViewHolderGrid)convertView.getTag();
        }
        convertView = viewHolderGrid.updateView(mData.get(position),isMul);
        return convertView;
    }
}
