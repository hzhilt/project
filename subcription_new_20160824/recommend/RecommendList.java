package com.meizu.flyme.calendar.subcription_new.recommend;

import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.Banner;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.Banners;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.Classify;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.Classifys;

/**
 * Created by huangzhihao on 16-8-15.
 */
public class RecommendList {

    public RecommendList(Banners banners, Classifys classifys) {
        this.banners = banners;
        this.classifys = classifys;
    }

    public Banners banners;

    public Classifys classifys;
}
