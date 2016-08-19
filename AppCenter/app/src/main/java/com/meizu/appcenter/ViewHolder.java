package com.meizu.appcenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.app.data.BaseData;

/**
 * Created by root on 14-11-5.
 */
public  class ViewHolder {

    private View view;

    protected View mConvertView = null;
    protected ViewGroup mParent;
    protected Context mContext;
    protected int mType;

    public ViewHolder(int type) {
        this.mType = type;
    }

    public static ViewHolder creatInstance(int type)
    {

        ViewHolder newViewHolder = null;

            if (type == 0) {
                newViewHolder = new ViewHolderViewPager(type);
            } else if (type == 1) {
                newViewHolder = new ViewHolderTwoPic(type);
            } else if (type == 3) {
                newViewHolder = new ViewHolderRecom(type);
            } else if (type == 2){
                newViewHolder = new ViewHolderContent(type);
            } else if (type == 4) {
                newViewHolder = new ViewHolderTwoApp(type);
            }
        return newViewHolder;
    }

    public View creatView(Context context){return view;};

    public View updateView(BaseData baseData){return view;}
}
