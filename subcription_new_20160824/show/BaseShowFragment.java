package com.meizu.flyme.calendar.subcription_new.show;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.RxAppFragment;

import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.RecyclerView;

/**
 * Created by huangzhihao on 16-8-24.
 */
public class BaseShowFragment extends RxAppFragment {

    String name;
    int id;
    RecyclerView recyclerView;


    public static BaseShowFragment newInstance(String name, int id) {
        BaseShowFragment fragment = new BaseShowFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putInt("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void getFragmentArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            id = bundle.getInt("id");
            setHasOptionsMenu(true);
            bundle.clear();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.subscribe_recommend, container, false);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.subscribe_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // TODO: 16-8-24
        // get data with id or name from server
        // construct object to adapter
        // show the data


        return mRootView;
    }
}
