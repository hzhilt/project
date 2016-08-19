package com.meizu.flyme.calendar.subcription_new.recommend.cards.show;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ItemViewProvider;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.TypeItem;

import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;

/**
 * Created by huangzhihao on 16-8-10.
 */
public class ShowItemViewProvider extends ItemViewProvider<ShowItemContent> {


    private class ViewHolder extends ItemViewProvider.ViewHolder {

        private MzRecyclerView mzRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            mzRecyclerView = (MzRecyclerView) itemView.findViewById(R.id.shows_recycleview_layout);
        }
    }


    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {
        View root = inflater.inflate(R.layout.subscribe_new_show_layout,null);
        ViewHolder viewHolder = new ViewHolder(root);
        root.setTag(viewHolder);
        return root;
    }

    @Override
    protected void onBindView(View view, ShowItemContent showItemContent, TypeItem typeItem) {

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mzRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        holder.mzRecyclerView.setAdapter(showItemContent.adapter);

        //movieItemContent.adapter.notifyDataSetChanged();

    }
}
