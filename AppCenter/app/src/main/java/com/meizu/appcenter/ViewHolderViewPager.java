package com.meizu.appcenter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meizu.app.data.BaseData;
import com.meizu.app.data.ViewPageData;

/**
 * Created by root on 14-11-6.
 */
public class ViewHolderViewPager extends ViewHolder {

    public ViewPager mViewPager;
    final int VIEWPAGER_SIZE = 3;

    public ViewHolderViewPager(int type) {
        super(type);
    }


    @Override
    public View creatView(Context context){

        View convertView = LayoutInflater.from(context).inflate(R.layout.listview_viewpager, mParent, false);
        this.mViewPager = (ViewPager) convertView.findViewById(R.id.viewpager);
        this.mConvertView = convertView;
        this.mContext = context;

        return convertView;
    }

    @Override
    public View updateView(final BaseData baseData) {

        final ViewPageData viewPageData = (ViewPageData)baseData;
        PagerAdapter mPagerAdapter = new PagerAdapter(){

            @Override
            public int getCount() {
                return VIEWPAGER_SIZE;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {;
                container.removeView((View)object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view = inflater.inflate(R.layout.viewpager_left, null);
                ImageView imageView  = (ImageView)view.findViewById(R.id.imageViewL);
                imageView.setImageResource(viewPageData.mImages.get(position).mImageID);
                container.addView(view);
                return view;
            }
        };
        this.mViewPager.setAdapter(mPagerAdapter);
        return this.mConvertView;
    }
}
