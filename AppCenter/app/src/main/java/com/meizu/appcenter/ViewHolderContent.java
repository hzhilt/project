package com.meizu.appcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.app.data.BaseData;
import com.meizu.app.data.OneAppData;

/**
 * Created by root on 14-11-6.
 */
public class ViewHolderContent extends ViewHolder {
    private ImageView mImageView;
    private TextView mName;
    private TextView mType;
    private TextView mLevel;
    private TextView mNum;
    private Button mButtonDownLoad;

    public ViewHolderContent(int type) {
        super(type);
    }

    @Override
    public View creatView(Context context) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.listview_content, mParent, false);
        mImageView = (ImageView) convertView.findViewById(R.id.imageViewL);
        mButtonDownLoad = (Button) convertView.findViewById(R.id.download);
        mButtonDownLoad.setBackgroundResource(R.drawable.button);
        mName = (TextView) convertView.findViewById(R.id.filename);
        mLevel = (TextView) convertView.findViewById(R.id.level);
        mNum = (TextView) convertView.findViewById(R.id.downloadnum);
        mType = (TextView) convertView.findViewById(R.id.type);

        mConvertView = convertView;

        return convertView;
    }

    @Override
    public View updateView(BaseData baseData) {
        OneAppData oneAppData = (OneAppData)baseData;
        mImageView.setImageResource(oneAppData.mImageID);
        mName.setText(oneAppData.mAppName);
        mLevel.setText(oneAppData.mLevel +"星");
        mNum.setText(Integer.toString(oneAppData.mDownloadNum));
        mType.setText(oneAppData.mType);
        return mConvertView;

    }
}
