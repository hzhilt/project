package com.meizu.flyme.calendar.subcription_new.recommend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.RxAppFragment;
import com.meizu.flyme.calendar.subcription_new.ApiService;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerAdapter;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.TypeItem;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.TypeItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.BannerItem;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.BannerItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.banner.BannerList;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.Classify;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.ClassifyItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.classify.ClassifyList;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.movie.MovieItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.show.ShowItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.cards.sport.NBAItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.head.CardHead;
import com.meizu.flyme.calendar.subcription_new.recommend.head.HeadItemFactory;
import com.meizu.flyme.calendar.subcription_new.recommend.response.DataListResponse;
import com.meizu.flyme.calendar.subcription_new.recommend.response.Datas;
import com.meizu.flyme.calendar.subcription_new.recommend.response.Info;
import com.meizu.flyme.calendar.subcription_new.recommend.response.ListResponse;
import com.meizu.flyme.calendar.subcription_new.recommend.response.NbaInfo;
import com.meizu.flyme.calendar.subcription_new.recommend.response.ShowInfo;
import com.meizu.flyme.calendar.subcription_new.recommend.response.VedioInfo;
import com.meizu.flyme.calendar.subscription.Logger;

import java.util.ArrayList;
import java.util.List;

import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.RecyclerView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangzhihao on 16-8-4.
 */
public class RecommendFragment extends RxAppFragment implements RecommendContract.View {

    private TypeItemFactory itemFactory;
    private RecyclerView recyclerView;
    private Context mContext;

    public RecommendFragment() {
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.subscribe_recommend, container, false);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.subscribe_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemFactory = new TypeItemFactory.Builder().build();
        mContext = this.getContext();

        List<TypeItem> typeItems = new ArrayList<>(80);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        Observable<Banners> banner = retrofit.create(ApiService.IRecommend.class).getRecommendBannerList();
//        Observable<Classifys> classify = retrofit.create(ApiService.IRecommend.class).getClassifyList();
//        //Observable<>
//
//
//        Observable.zip(banner,
//                classify, new Func2<Banners, Classifys, RecommendList>() {
//                    @Override
//                    public RecommendList call(Banners banners, Classifys classifys) {
//                        return new RecommendList(banners,classifys);
//                    }
//                }
//        ).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<RecommendList>() {
//                    @Override
//                    public void call(RecommendList recommend) {
//                        List<TypeItem> typeItems = new ArrayList<>(80);
//                        TypeItem banner = itemFactory.newItem(new BannerItemContent(recommend.banners));
//                        typeItems.add(banner);
//                        ItemTypePool.register(BannerItemContent.class, new BannerItemViewProvider());
//
//                        TypeItem classify = itemFactory.newItem(new ClassifyItemContent(mContext,recommend.classifys.getValue()));
//                        typeItems.add(classify);
//                        ItemTypePool.register(ClassifyItemContent.class, new ClassifyItemViewProvider());
//
//                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//                        recyclerView.setAdapter(new TypeItemsAdapter(typeItems));
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//                });



        retrofit.create(ApiService.IRecommend.class).getContentList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ListResponse<DataListResponse<Info>>>() {
                    @Override
                    public void call(ListResponse<DataListResponse<Info>> dataListResponseListResponse) {

                        int size = dataListResponseListResponse.getValue().size();

                        List<Object> objects = new ArrayList<Object>();

                        for (DataListResponse<Info> info : dataListResponseListResponse.getValue()) {

                            String type = info.getType();

                            if (type.equals("banner")) {

                                BannerList bannerList = new BannerList();

                                for (Info i : info.getData()) {
                                    // 组装 Banner list
                                    // BannerItem
                                    //
                                    BannerItem bannerItem = new BannerItem();
                                    bannerItem.copy(i);
                                    bannerList.getmList().add(bannerItem);
                                }
                                objects.add(bannerList);
                                //break;
                            } else if (type.equals("cates")) {

                                ClassifyList classifyList = new ClassifyList();

                                for (Info i : info.getData()) {
                                    // 组装 Cates list
                                    // CatesItem
                                    Classify classify = new Classify();
                                    classify.copy(i);
                                    classifyList.getmList().add(classify);

                                }
                                objects.add(classifyList);// add to object
                                //break;
                            } else if (type.equals("datas")) {

                                for (Info i : info.getData()) {

                                    int template = i.getTemplate();

                                    switch (template) {
                                        case 1:
                                            //NBA
                                            String subTitle = i.getTitle();

                                            CardHead NBAHead = new CardHead();
                                            NBAHead.setSubTitle(subTitle);
                                            NBAHead.setItemId(i.getId());

                                            objects.add(NBAHead);


                                            //break;

                                            List<Datas> nba = i.getDatas();
                                            List<NbaInfo> nbaInfos = new ArrayList<NbaInfo>(nba.size());

                                            //nbaInfos.add(nba.get(0));

                                            for (Datas datas:nba) {
                                                NbaInfo nbaInfo = new NbaInfo();
                                                nbaInfo.copy(datas);
                                                nbaInfos.add(nbaInfo);
                                                objects.add(nbaInfo);
                                            }


                                            //组装 NBA

                                            //objects.add(NBA);

                                            break;
                                        case 2:
                                            //movie

                                            String movieTitle = i.getTitle();

                                            CardHead movieHead = new CardHead();
                                            movieHead.setSubTitle(movieTitle);
                                            movieHead.setItemId(i.getId());

                                            objects.add(movieHead);


                                            List<Datas> movie = i.getDatas();

                                            List<VedioInfo> vedioInfos = new ArrayList<VedioInfo>(movie.size());

                                            for (Datas datas:movie) {
                                                VedioInfo vedioInfo = new VedioInfo();
                                                vedioInfo.copy(datas);
                                                vedioInfos.add(vedioInfo);
                                                objects.add(vedioInfo);
                                            }

                                            break;
                                        case 3:
                                            //show

                                            String showTitle = i.getTitle();

                                            CardHead showHead = new CardHead();
                                            showHead.setSubTitle(showTitle);
                                            showHead.setItemId(i.getId());

                                            objects.add(showHead);

                                            List<Datas> show = i.getDatas();
                                            List<ShowInfo> showInfos = new ArrayList<ShowInfo>(show.size());
                                            for (Datas datas:show) {
                                                ShowInfo showInfo = new ShowInfo();
                                                showInfo.copy(datas);
                                                showInfos.add(showInfo);
                                                objects.add(showInfo);
                                            }

                                            //组装 show

                                            break;
                                        case 4:
                                            //
                                            break;
                                        case 5:
                                            //
                                            break;

                                        default:
                                            //


                                            break;
                                    }

                                }
                            }
                            //int objectSize = objects.size();
                        }

                        AssemblyRecyclerAdapter adapter = new AssemblyRecyclerAdapter(objects);
                        adapter.addItemFactory(new BannerItemFactory());
                        adapter.addItemFactory(new ClassifyItemFactory());
                        adapter.addItemFactory(new HeadItemFactory());
                        adapter.addItemFactory(new NBAItemFactory());
                        adapter.addItemFactory(new MovieItemFactory());
                        adapter.addItemFactory(new ShowItemFactory());
                        recyclerView.setAdapter(adapter);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.i(throwable.getMessage());
                    }
                });

//        TypeItem banner = itemFactory.newItem(new BannerItemContent(null));
//        TypeItem classify = itemFactory.newItem(new ClassifyItemContent(this.getContext(),null));
//        TypeItem movie = itemFactory.newItem(new MovieItemContent(null));
//        TypeItem show = itemFactory.newItem(new ShowItemContent(null));
//
//        typeItems.add(banner);
//        typeItems.add(classify);
//        typeItems.add(movie);
//        typeItems.add(show);
//
//
//        ItemTypePool.register(ClassifyItemContent.class, new ClassifyItemViewProvider());
//        ItemTypePool.register(BannerItemContent.class, new BannerItemViewProvider());
//        ItemTypePool.register(MovieItemContent.class, new MovieItemViewProvider());
//        ItemTypePool.register(ShowItemContent.class, new ShowItemViewProvider());
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        recyclerView.setAdapter(new TypeItemsAdapter(typeItems));
        return mRootView;
    }
}
