package com.meizu.appcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.app.data.BaseData;
import com.meizu.app.data.RecomData;

/**
 * Created by root on 14-11-6.
 */
public class ViewHolderRecom extends ViewHolder {
    private ImageView mTuiJianImageView;
    private TextView mTuiJian;
    private TextView mMore;

    public ViewHolderRecom(int type) {
        super(type);
    }


    @Override
    public View creatView(Context context) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.listview_more, mParent, false);
        this.mTuiJianImageView = (ImageView)convertView.findViewById(R.id.moreImage);
        this.mTuiJian = (TextView)convertView.findViewById(R.id.tuijian);
        this.mMore = (TextView)convertView.findViewById(R.id.more);
        this.mConvertView = convertView;
        return convertView;
    }

    @Override
    public View updateView(BaseData baseData){

        RecomData recomData = (RecomData)baseData;
        this.mTuiJianImageView.setImageResource(recomData.mImageID);
        this.mTuiJian.setText(recomData.mRecom);
        this.mMore.setText(recomData.mMore);
        return this.mConvertView;
    }
}
