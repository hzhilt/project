package com.meizu.flyme.calendar.subcription_new.recommend.cards.banner;

import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ItemContent;

import java.util.List;

/**
 * Created by huangzhihao on 16-8-8.
 */
public class BannerItemContent implements ItemContent {

    Banners mItem;

    public BannerItemContent(Banners banners) {

        if (mItem == null) {
            //mItem = createData();
        }

        this.mItem = banners;
    }

//    private List<Banners> createData() {
//        List<Banners> items = new ArrayList<>();
//
//        BannerItem bannerItem = new BannerItem();
//
//        bannerItem.setColumnId(100);
//        bannerItem.setIconUrl("http://bbsimage.meizu.com/forum/201608/08/094953k7jw777gkok0ybz7.jpg");
//        bannerItem.setName("hello");
//        bannerItem.setOrder("all");
//        bannerItem.setType(BannerItem.TYPE_URL);
//        bannerItem.setUrl("http://www.360.com");
//
//        for (int i = 0;  i <= 5; i++) {
//            items.add(bannerItem);
//        }
//
//        return items;
//
//    }
}
