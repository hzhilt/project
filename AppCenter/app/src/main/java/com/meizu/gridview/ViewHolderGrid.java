package com.meizu.gridview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.meizu.appcenter.R;

/**
 * Created by root on 14-11-10.
 */
public class ViewHolderGrid {

    public ImageView mImageView;
    public ImageView mImageGrey;
    public  ImageView mCheckBox;
    public View mView;

    public View creatView(Context context){
        mView = LayoutInflater.from(context).inflate(R.layout.item,null);
        mImageView = (ImageView) mView.findViewById(R.id.imageView);
        mImageGrey = (ImageView) mView.findViewById(R.id.imageViewTop);
        mCheckBox = (ImageView) mView.findViewById(R.id.checkBox);
        return mView;
    }

    public View updateView(Data data,boolean status){

        Context context= mView.getContext();
        Drawable drawable = context.getResources().getDrawable(R.drawable.shape_grid);
        GradientDrawable shapeDrawable = (GradientDrawable)drawable;
        shapeDrawable.setColor(Color.parseColor(data.mColorID));

        mCheckBox.setBackgroundResource(R.drawable.ic_game_selected_nm);
        boolean itemStatus = data.mStatus;

        if(status == true) {
            mCheckBox.setVisibility(View.VISIBLE);
            mCheckBox.setClickable(true);
            if (itemStatus){
                //mCheckBox.setChecked(itemStatus);
                //mImageView.getBackground().setAlpha(100);//变暗
                mImageGrey.setVisibility(View.VISIBLE);
                mCheckBox.setBackgroundResource(R.drawable.ic_game_selected_on);
            }
            else{
                //mCheckBox.setChecked(itemStatus);
                mImageGrey.setVisibility(View.INVISIBLE);
                mCheckBox.setBackgroundResource(R.drawable.ic_game_selected_nm);
                mImageView.getBackground().setAlpha(255);
            }
        }
        else {
            mCheckBox.setVisibility(View.INVISIBLE);
            mImageGrey.setVisibility(View.INVISIBLE);
        }
        return mView;
    }



}
