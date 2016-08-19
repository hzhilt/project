package com.meizu.flyme.calendar.subcription_new.recommend.cards.movie;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.common.widget.CircularProgressButton;
import com.meizu.common.widget.RoundCornerImageView;
import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItem;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.response.VedioInfo;
import com.meizu.flyme.calendar.subscription.ImageLoaderUtils;

/**
 * Created by huangzhihao on 16-8-23.
 */
public class MovieItemFactory extends AssemblyRecyclerItemFactory<MovieItemFactory.MovieItem>{

    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof VedioInfo;
    }

    @Override
    public MovieItem createAssemblyItem(ViewGroup parent) {
        return new MovieItem(R.layout.subscribe_new_movie_item_relative,parent);
    }

    public class MovieItem extends AssemblyRecyclerItem<VedioInfo> {

        RoundCornerImageView img;
        TextView name;
        TextView time;
        TextView desc;
        TextView counts;
        CircularProgressButton button;

        public MovieItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {
            img = (RoundCornerImageView) findViewById(R.id.movie_icon);
            time = (TextView) findViewById(R.id.movie_time);
            desc = (TextView) findViewById(R.id.movie_desc);
            counts = (TextView) findViewById(R.id.movie_counts);
            name = (TextView) findViewById(R.id.movie_name);
            button = (CircularProgressButton) findViewById(R.id.movie_action);
        }

        @Override
        protected void onConfigViews(Context context) {

        }

        @Override
        protected void onSetData(int position, VedioInfo vedioInfo) {

            time.setText(vedioInfo.getShowDateStr());
            desc.setText(vedioInfo.getLabel());
            counts.setText(vedioInfo.getSubscribeCount() + "");
            name.setText(vedioInfo.getName());
            ImageLoaderUtils.displayImage(vedioInfo.getImg(), img, R.drawable.ic_account_editor, null);

        }
    }
}
