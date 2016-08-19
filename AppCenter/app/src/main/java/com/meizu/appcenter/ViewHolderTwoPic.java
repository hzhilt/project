package com.meizu.appcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.meizu.app.data.BaseData;
import com.meizu.app.data.TwoPicData;

/**
 * Created by root on 14-11-6.
 */
public class ViewHolderTwoPic extends ViewHolder {
    private ImageView mImageLeft;
    private ImageView mImageRight;

    public ViewHolderTwoPic(int type) {
        super(type);
    }

    @Override
    public View creatView(Context context){
        View convertView = LayoutInflater.from(context).inflate(R.layout.listview_pic, null);
        mImageLeft = (ImageView) convertView.findViewById(R.id.imageViewLeft);
        mImageRight = (ImageView) convertView.findViewById(R.id.imageViewRight);
        mConvertView = convertView;

        return convertView;
    }

    @Override
    public View updateView(BaseData baseData){
        TwoPicData twoPicData = (TwoPicData)baseData;
        mImageLeft.setImageResource(twoPicData.mImageID1);
        mImageRight.setImageResource(twoPicData.mImageID2);

        return mConvertView;
    }
}
