package com.meizu.flyme.calendar.subcription_new.recommend.cards.show;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.common.widget.CircularProgressButton;
import com.meizu.flyme.calendar.R;

import java.util.List;

import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;

/**
 * Created by huangzhihao on 16-8-10.
 */
public class ShowItemAdapter extends MzRecyclerView.Adapter{

    private List<Show> mShows;

    public ShowItemAdapter(List<Show> shows) {
        mShows = shows;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_new_show_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.show_name.setText(mShows.get(position).getName());
        //viewHolder.show_counts.setText(mshows.get(position).getCounts());
        viewHolder.show_desc.setText(mShows.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mShows.size();
    }


    private class ViewHolder extends MzRecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView show_icon;
        private TextView show_name;
        //private TextView show_time;
        private TextView show_desc;
        private TextView show_counts;
        private CircularProgressButton show_action;
        private int position;


        public ViewHolder(View itemView) {
            super(itemView);
            show_icon = (ImageView) itemView.findViewById(R.id.show_icon);
            show_name = (TextView) itemView.findViewById(R.id.show_name);
            //show_time = (TextView) itemView.findViewById(R.id.show_time);
            show_desc = (TextView) itemView.findViewById(R.id.show_desc);
            show_counts = (TextView) itemView.findViewById(R.id.show_counts);
            show_action = (CircularProgressButton) itemView.findViewById(R.id.show_action);
        }


        @Override
        public void onClick(View v) {
            if (v == show_action) {

            }
        }
    }
}
