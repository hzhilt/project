package com.meizu.flyme.calendar.subcription_new.recommend.cards.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.common.widget.CircularProgressButton;
import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.BaseViewHolder;

import java.util.List;

import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;

/**
 * Created by huangzhihao on 16-8-9.
 */
public class MovieItemAdapter extends MzRecyclerView.Adapter {


    private List<Movie> mMovies;


    public MovieItemAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        BaseViewHolder viewHolder;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_new_movie_item,null);
            viewHolder = new ViewHolder1(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_new_movie_item2,null);
            viewHolder = new ViewHolder2(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (mMovies.get(position).getType() == 1) {
            ViewHolder1 viewHolder = (ViewHolder1) holder;
            viewHolder.movie_name.setText(mMovies.get(position).getName());
            //viewHolder.movie_counts.setText(mMovies.get(position).getCounts());
            viewHolder.movie_desc.setText(mMovies.get(position).getDescription());
        } else {
            ViewHolder2 viewHolder = (ViewHolder2) holder;
            viewHolder.movie_name.setText(mMovies.get(position).getName());
            //viewHolder.movie_counts.setText(mMovies.get(position).getCounts());
            viewHolder.movie_desc.setText(mMovies.get(position).getDescription());
        }



    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    @Override
    public int getItemViewType(int position) {
        return mMovies.get(position).getType();
    }

    private class ViewHolder1 extends BaseViewHolder {

        private ImageView movie_icon;
        private TextView movie_name;
        private TextView movie_time;
        private TextView movie_desc;
        private TextView movie_counts;
        private CircularProgressButton movie_action;
        private int position;


        public ViewHolder1(View itemView) {
            super(itemView);
            movie_icon = (ImageView) itemView.findViewById(R.id.movie_icon);
            movie_name = (TextView) itemView.findViewById(R.id.movie_name);
            movie_time = (TextView) itemView.findViewById(R.id.movie_time);
            movie_desc = (TextView) itemView.findViewById(R.id.movie_desc);
            movie_counts = (TextView) itemView.findViewById(R.id.movie_counts);
            movie_action = (CircularProgressButton) itemView.findViewById(R.id.movie_action);
        }
    }

    private class ViewHolder2 extends BaseViewHolder {

        private ImageView movie_icon;
        private TextView movie_name;
        private TextView movie_time;
        private TextView movie_desc;
        private TextView movie_counts;
        private CircularProgressButton movie_action;
        private int position;


        public ViewHolder2(View itemView) {
            super(itemView);
            movie_icon = (ImageView) itemView.findViewById(R.id.movie_icon);
            movie_name = (TextView) itemView.findViewById(R.id.movie_name);
            movie_time = (TextView) itemView.findViewById(R.id.movie_time);
            movie_desc = (TextView) itemView.findViewById(R.id.movie_desc);
            movie_counts = (TextView) itemView.findViewById(R.id.movie_counts);
            movie_action = (CircularProgressButton) itemView.findViewById(R.id.movie_action);
        }
    }
}
