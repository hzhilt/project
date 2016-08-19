package com.meizu.flyme.calendar.subcription_new.recommend;

import android.content.Context;

import com.meizu.flyme.calendar.AppApplication;
import com.meizu.flyme.calendar.dateview.datasource.recommend.SubscribeItem;
import com.meizu.flyme.calendar.subcription_new.ApiService;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.Banner;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.BannerItem;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.Banners;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.Classify;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.Classifys;
import com.meizu.flyme.calendar.subscription.api.ApiUtility;
import com.meizu.flyme.calendar.subscription.api.ServerApi;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huangzhihao on 16-8-15.
 */
public class ApiServiceImpl {

    private static ApiServiceImpl mApiService = null;

    private ApiService mService;

    private ApiServiceImpl() {
        initService();
    }

    public static synchronized ApiServiceImpl get() {
        if (mApiService == null) {
            mApiService = new ApiServiceImpl();
        }

        return mApiService;
    }


    public synchronized static Observable<Banners> getRecommendBannerList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.IRecommend.class).getRecommendBannerList()
                .subscribeOn(Schedulers.io())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public synchronized static Observable<Classifys> getClassifyList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.IRecommend.class).getClassifyList()
                .subscribeOn(Schedulers.io())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }




    private void initService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new ApiInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mService = retrofit.create(ApiService.class);
    }

    class ApiInterceptor implements Interceptor {
        private final Context mContext;

        public ApiInterceptor() {
            mContext = AppApplication.getInstance().getApplicationContext();
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            // imei
            response.header("i", ApiUtility.getImei(mContext));
            // phone model
            response.header("phv", ApiUtility.getPhoneModel());
            // rom version
            response.header("osv", ApiUtility.getRomVersion());
            // client version
            response.header("cv", ApiUtility.getClientVersion());
            // net type
            response.header("nt", ApiUtility.getNetWorkType(mContext));
            // sim operator
            response.header("op", ApiUtility.getCarrierOperator(mContext));
            return response;
        }
    }
}
