package com.meizu.flyme.calendar.subcription_new.recommend.cards.banner;

import android.content.Context;
import android.view.ViewGroup;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItem;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.BannerView;

/**
 * Created by huangzhihao on 16-8-23.
 */
public class BannerItemFactory extends AssemblyRecyclerItemFactory<BannerItemFactory.BannerItem> {

    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof BannerList;
    }

    @Override
    public BannerItem createAssemblyItem(ViewGroup parent) {
        return new BannerItem(R.layout.header_banner, parent);
    }

    public class BannerItem extends AssemblyRecyclerItem<BannerList> {

        BannerView banner;

        public BannerItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {
            banner = (BannerView) findViewById(R.id.banner);
        }

        @Override
        protected void onConfigViews(Context context) {

        }

        @Override
        protected void onSetData(int position, BannerList bannerList) {
            banner.setupItems(bannerList.getmList());
        }


    }
}
