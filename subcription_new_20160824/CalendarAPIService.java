package com.meizu.flyme.calendar.subcription_new;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by huangzhihao on 16-8-8.
 */
public interface CalendarAPIService {

    public static final String BASE_HOST = "http://cal.meizu.com";

    @GET("/android/unauth/subscribecard/list.do")
    Observable<String> getRecommendCardList();

    class Factory {
        public static CalendarAPIService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(CalendarAPIService.class);
        }
    }
}
