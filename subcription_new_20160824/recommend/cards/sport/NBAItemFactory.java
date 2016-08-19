package com.meizu.flyme.calendar.subcription_new.recommend.cards.sport;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.common.widget.CircularProgressButton;
import com.meizu.common.widget.RoundCornerImageView;
import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItem;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.response.NbaInfo;
import com.meizu.flyme.calendar.subscription.ImageLoaderUtils;

/**
 * Created by huangzhihao on 16-8-23.
 */
public class NBAItemFactory extends AssemblyRecyclerItemFactory<NBAItemFactory.NBAItem> {

    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof NbaInfo;
    }

    @Override
    public NBAItem createAssemblyItem(ViewGroup parent) {
        return new NBAItem(R.layout.subscribe_new_layout_nba_item,parent);
    }

    public class NBAItem extends AssemblyRecyclerItem<NbaInfo> {

        TextView time;
        TextView desc;
        TextView counts;
        TextView team1;
        TextView team2;
        RoundCornerImageView img1;
        RoundCornerImageView img2;
        CircularProgressButton sub;

        public NBAItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {

            time = (TextView) findViewById(R.id.time);
            desc = (TextView) findViewById(R.id.desc);
            counts = (TextView) findViewById(R.id.counts);
            team1 = (TextView) findViewById(R.id.team1);
            team2 = (TextView) findViewById(R.id.team2);

            img1 = (RoundCornerImageView) findViewById(R.id.img1);
            img2 = (RoundCornerImageView) findViewById(R.id.img2);

            sub = (CircularProgressButton) findViewById(R.id.subscribe);

        }

        @Override
        protected void onConfigViews(Context context) {

        }

        @Override
        protected void onSetData(int position, NbaInfo nbaInfo) {
            time.setText(nbaInfo.getShowDateStr());
            desc.setText(nbaInfo.getLabel());
            counts.setText(nbaInfo.getSubscribeCount()+"");
            team1.setText(nbaInfo.getTitle1());
            team2.setText(nbaInfo.getTitle2());
            ImageLoaderUtils.displayImage(nbaInfo.getImg1(), img1, R.drawable.ic_account_editor, null);
            ImageLoaderUtils.displayImage(nbaInfo.getImg2(), img2, R.drawable.ic_account_editor, null);

        }
    }
}
