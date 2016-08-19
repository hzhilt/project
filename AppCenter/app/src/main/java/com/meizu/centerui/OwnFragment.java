package com.meizu.centerui;


import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.appcenter.R;

/**
 * Created by root on 14-12-22.
 */
public class OwnFragment extends Fragment {

    private ActionBar mActionBar;
    private View mCustomView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.centerui_content, container, false);
        mActionBar = getActivity().getActionBar();

        mCustomView = inflater.inflate(R.layout.centerui_personitem, null);
        mActionBar.setCustomView(mCustomView);
        TextView text = (TextView) v.findViewById(android.R.id.text1);
        text.setText("Own");
        return v;
    }
}
