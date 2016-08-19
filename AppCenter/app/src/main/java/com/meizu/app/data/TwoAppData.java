package com.meizu.app.data;

/**
 * Created by root on 14-11-7.
 */
public class TwoAppData extends BaseData {

    public OneAppData mOneAppDataLeft;
    public OneAppData mOneAppDataRight;

    public TwoAppData() {
        VIEW_TYPE = 4;
        mOneAppDataLeft = new OneAppData();
        mOneAppDataRight = new OneAppData();
    }

}
