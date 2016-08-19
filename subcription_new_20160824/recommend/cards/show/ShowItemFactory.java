package com.meizu.flyme.calendar.subcription_new.recommend.cards.show;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.common.widget.CircularProgressButton;
import com.meizu.common.widget.RoundCornerImageView;
import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItem;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.response.ShowInfo;
import com.meizu.flyme.calendar.subscription.ImageLoaderUtils;

/**
 * Created by huangzhihao on 16-8-23.
 */
public class ShowItemFactory extends AssemblyRecyclerItemFactory<ShowItemFactory.ShowItem> {

    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof ShowInfo;
    }

    @Override
    public ShowItem createAssemblyItem(ViewGroup parent) {
        return new ShowItem(R.layout.subscribe_new_show_item_relative,parent);
    }


    public class ShowItem extends AssemblyRecyclerItem<ShowInfo> {

        RoundCornerImageView img;
        TextView name;
        //TextView time;
        TextView desc;
        TextView counts;
        CircularProgressButton button;
        
        public ShowItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {
            img = (RoundCornerImageView) findViewById(R.id.show_icon);
            //time = (TextView) findViewById(R.id.show_time);
            desc = (TextView) findViewById(R.id.show_desc);
            counts = (TextView) findViewById(R.id.show_counts);
            name = (TextView) findViewById(R.id.show_name);
            button = (CircularProgressButton) findViewById(R.id.show_action);
        }

        @Override
        protected void onConfigViews(Context context) {

        }

        @Override
        protected void onSetData(int position, ShowInfo showInfo) {
            //time.setText(vedioInfo.getShowDateStr());
            desc.setText(showInfo.getLabel());
            counts.setText(showInfo.getSubscribeCount() + "");
            name.setText(showInfo.getName());
            ImageLoaderUtils.displayImage(showInfo.getImg(), img, R.drawable.ic_account_editor, null);
        }

    }
}
