package com.meizu.app.data;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-11-7.
 */
public class ViewPageData extends BaseData {

    public List<ImageInfo> mImages = new ArrayList<ImageInfo>();

    public ViewPageData(){
        this.VIEW_TYPE = 0;
    }
}
