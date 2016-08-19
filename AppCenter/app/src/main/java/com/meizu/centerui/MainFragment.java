package com.meizu.centerui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.meizu.app.data.BaseData;
import com.meizu.app.data.ImageInfo;
import com.meizu.app.data.OneAppData;
import com.meizu.app.data.RecomData;
import com.meizu.app.data.TwoAppData;
import com.meizu.app.data.TwoPicData;
import com.meizu.app.data.ViewPageData;
import com.meizu.appcenter.MyAdapter;
import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 14-12-24.
 */
public class MainFragment extends Fragment {

    private ListView mListView;
    private MyAdapter mMyAdapter;
    private List<BaseData> mBaseData= new ArrayList<BaseData>();
    private String[] mType = {"游戏","社交","多媒体","工具","阅读","生活","休闲"};
    private String[] mLevel = {"1","2","3","4","5"};
    private String STRING_NAME ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("here", "MainF onCreateView");
        view = inflater.inflate(R.layout.activity_main,container,false);
        creatData();
        mListView = (ListView)view.findViewById(R.id.listView);
        //mListView.addFooterView();
        mMyAdapter = new MyAdapter(getActivity().getApplicationContext(),mBaseData);
        mListView.setAdapter(mMyAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("here", "MainF onResume");
        mListView.setAdapter(mMyAdapter);
    }

    private void creatData() {

        if(!mBaseData.isEmpty()){
            return ;
        }

        //part 1
        ViewPageData viewPageData = new ViewPageData();
        for(int i=0;i<3;i++)
        {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.mImageID = R.drawable.ic_launcher;
            viewPageData.mImages.add(imageInfo);
        }
        mBaseData.add(viewPageData);
        //part 2
        TwoPicData twoPicData = new TwoPicData();
        twoPicData.mImageID1 = R.drawable.ic_launcher;
        twoPicData.mImageID2 = R.drawable.ic_launcher;
        mBaseData.add(twoPicData);
        //part 3
        RecomData recomData =new RecomData();
        recomData.mImageID = R.drawable.ic_launcher;
        recomData.mRecom = "编辑推荐";
        recomData.mMore = "more";
        mBaseData.add(recomData);
        //part 4
        for(int i=0;i<10;i++)
        {
            OneAppData oneAppData = new OneAppData();
            oneAppData.mImageID = R.drawable.ic_launcher;
            oneAppData.mAppName = getRandomString(10);
            oneAppData.mLevel = getLevel();
            oneAppData.mDownloadNum = getNum(2000);
            oneAppData.mType = getType();
            mBaseData.add(oneAppData);
        }
        //part 5
        TwoAppData twoAppData = new TwoAppData();
        twoAppData.mOneAppDataLeft.mImageID = R.drawable.ic_launcher;
        twoAppData.mOneAppDataLeft.mAppName = getRandomString(10);
        twoAppData.mOneAppDataLeft.mLevel = getLevel();
        twoAppData.mOneAppDataLeft.mDownloadNum = getNum(3000);

        twoAppData.mOneAppDataRight.mImageID = R.drawable.ic_launcher;
        twoAppData.mOneAppDataRight.mAppName = getRandomString(10);
        twoAppData.mOneAppDataRight.mLevel = getLevel();
        twoAppData.mOneAppDataRight.mDownloadNum = getNum(3000);

        mBaseData.add(twoAppData);
    }

    // creat random data
    public String getRandomString(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(STRING_NAME.charAt(number));
        }
        return sb.toString();
    }

    public String getType()
    {
        Random random=new Random();
        String type = mType[random.nextInt(7)];
        return type;
    }

    public String getLevel()
    {
        Random random=new Random();
        String level = mLevel[random.nextInt(5)];
        return level;
    }

    public int  getNum(int n)
    {
        Random random=new Random();
        return random.nextInt(n);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("here", "MainF onCreate");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("here", "MainF onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("here", "MainF onStart");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("here", "MainF onActivityCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("here", "MainF onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("here", "MainF onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("here", "MainF onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("here", "MainF onDetach");
    }

}
