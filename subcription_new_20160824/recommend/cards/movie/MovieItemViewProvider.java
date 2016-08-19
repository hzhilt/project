package com.meizu.flyme.calendar.subcription_new.recommend.cards.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ItemViewProvider;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.BaseMultiItemAdapter;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.TypeItem;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ViewHolder;

import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;

/**
 * Created by huangzhihao on 16-8-9.
 */
public class MovieItemViewProvider extends ItemViewProvider<MovieItemContent> {

    private class ViewHolder extends ItemViewProvider.ViewHolder {

        private MzRecyclerView mzRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            mzRecyclerView = (MzRecyclerView) itemView.findViewById(R.id.movies_recycleview_layout);
            mzRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }


    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {

        View root = inflater.inflate(R.layout.subscribe_new_movie_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(root);
        root.setTag(viewHolder);

        return root;
    }

    @Override
    protected void onBindView(View view, final MovieItemContent movieItemContent, TypeItem typeItem) {

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mzRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //holder.mzRecyclerView.setB


        BaseMultiItemAdapter adapter = new BaseMultiItemAdapter(view.getContext(),movieItemContent.movies,movieItemContent) {
            @Override
            public void convert(com.meizu.flyme.calendar.subcription_new.recommend.basecard.ViewHolder holder, Object o) {

            }
        };

        holder.mzRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, MzRecyclerView.ViewHolder holder, int position) {
                Toast.makeText(view.getContext(),"you click item position is " + position,Toast.LENGTH_LONG).show();

            }
        });
        //movieItemContent.adapter.notifyDataSetChanged();

    }
}
