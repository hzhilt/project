package com.meizu.flyme.calendar.subcription_new;

import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.Banners;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.Classify;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.Classifys;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.content.Content;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.movie.Movie;
import com.meizu.flyme.calendar.subcription_new.recommend.response.DataListResponse;
import com.meizu.flyme.calendar.subcription_new.recommend.response.Info;
import com.meizu.flyme.calendar.subcription_new.recommend.response.ListResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by huangzhihao on 16-8-15.
 */
public class ApiService {

    public static final String HOST = "http://172.17.136.201:12307";

    public interface IRecommend {
        @GET("/banner")
        Observable<Banners> getRecommendBannerList();

        @GET("/classify")
        Observable<Classifys> getClassifyList();

        @GET("/content")
        Observable<ListResponse<DataListResponse<Info>>> getContentList();
    }
}
