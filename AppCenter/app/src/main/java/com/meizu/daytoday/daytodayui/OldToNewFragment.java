package com.meizu.daytoday.daytodayui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.appcenter.R;

/**
 * Created by huangzhihao on 15-6-11.
 */
public class OldToNewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.daytoday_oldtonew, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
