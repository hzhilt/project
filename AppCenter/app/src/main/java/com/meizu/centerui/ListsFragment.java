package com.meizu.centerui;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.appcenter.R;

/**
 * Created by root on 14-12-22.
 */
public class ListsFragment extends Fragment {

    private ActionBar mActionBar;
    private View mCustomView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("here", "ListF onCreateView");
        View v = inflater.inflate(R.layout.centerui_content, container, false);
        mActionBar = getActivity().getActionBar();
        mCustomView = inflater.inflate(R.layout.centerui_listitem, null);
        mActionBar.setCustomView(mCustomView);
        TextView text = (TextView) v.findViewById(android.R.id.text1);
        text.setText("Lists");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("here", "ListF onResume");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("here", "ListF onCreate");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("here", "ListF onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("here", "ListF onStart");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("here", "ListF onActivityCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("here", "ListF onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("here", "ListF onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("here", "ListF onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("here", "ListF onDetach");
    }



}
