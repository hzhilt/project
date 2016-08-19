package com.meizu.appcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.app.data.BaseData;
import com.meizu.app.data.TwoAppData;

/**
 * Created by root on 14-11-7.
 */
public class ViewHolderTwoApp extends ViewHolder {
    //left
    private ImageView mImageViewL;
    private TextView mNameL;
    private TextView mLevelL;
    private TextView mNumL;
    private Button mButtonDownLoadL;
    //right
    private ImageView mImageViewR;
    private TextView mNameR;
    private TextView mLevelR;
    private TextView mNumR;
    private Button mButtonDownLoadR;

    private View leftView;
    private View rightView;

    public ViewHolderTwoApp(int type) {
        super(type);
    }
    @Override
    public View creatView(Context context) {

        View convertView = LayoutInflater.from(context).inflate(R.layout.listview_twoapp,null);
        leftView = convertView.findViewById(R.id.left);
        rightView = convertView.findViewById(R.id.right);

        mImageViewL = (ImageView)leftView.findViewById(R.id.imageView);
        mNameL = (TextView)leftView.findViewById(R.id.filename);
        mLevelL = (TextView)leftView.findViewById(R.id.level);
        mNumL = (TextView)leftView.findViewById(R.id.downloadnum);
        mButtonDownLoadL = (Button)leftView.findViewById(R.id.download);
        mButtonDownLoadL.setBackgroundResource(R.drawable.button);

        mImageViewR = (ImageView)rightView.findViewById(R.id.imageView);
        mNameR = (TextView)rightView.findViewById(R.id.filename);
        mLevelR = (TextView)rightView.findViewById(R.id.level);
        mNumR = (TextView)rightView.findViewById(R.id.downloadnum);
        mButtonDownLoadR = (Button)rightView.findViewById(R.id.download);
        mButtonDownLoadR.setBackgroundResource(R.drawable.button);

        mConvertView = convertView;
        return convertView;
    }

    @Override
    public View updateView(BaseData baseData) {
        TwoAppData twoAppData = (TwoAppData)baseData;
        mImageViewL.setImageResource(twoAppData.mOneAppDataLeft.mImageID);
        mButtonDownLoadL.setText("download");
        mNameL.setText(twoAppData.mOneAppDataLeft.mAppName);
        mLevelL.setText(twoAppData.mOneAppDataLeft.mLevel +"星");
        mNumL.setText(Integer.toString(twoAppData.mOneAppDataLeft.mDownloadNum));

        mImageViewR.setImageResource(twoAppData.mOneAppDataRight.mImageID);
        mButtonDownLoadR.setText("download");
        mNameR.setText(twoAppData.mOneAppDataRight.mAppName);
        mLevelR.setText(twoAppData.mOneAppDataRight.mLevel +"星");
        mNumR.setText(Integer.toString(twoAppData.mOneAppDataRight.mDownloadNum));
        return mConvertView;

    }
}
