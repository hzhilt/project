package com.meizu.appcenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.meizu.app.data.BaseData;
import com.meizu.app.data.ImageInfo;
import com.meizu.app.data.OneAppData;
import com.meizu.app.data.RecomData;
import com.meizu.app.data.TwoAppData;
import com.meizu.app.data.TwoPicData;
import com.meizu.app.data.ViewPageData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends Activity {

    private ListView mListView;
    private MyAdapter mMyAdapter;
    private Toolbar mToolBar;
    private List<BaseData> mBaseData = new ArrayList<BaseData>();
    private String[] mType = {"游戏", "社交", "多媒体", "工具", "阅读", "生活", "休闲"};
    private String[] mLevel = {"1", "2", "3", "4", "5"};
    private String STRING_NAME = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitle("hello the world!");

        creatData();
        mListView = (ListView) findViewById(R.id.listView);
        //mListView.addFooterView();
        mMyAdapter = new MyAdapter(this, mBaseData);
        mListView.setAdapter(mMyAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                return true;

            case R.id.item2:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void creatData() {
        //part 1
        ViewPageData viewPageData = new ViewPageData();
        for (int i = 0; i < 3; i++) {
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

        RecomData recomData = new RecomData();
        recomData.mImageID = R.drawable.ic_launcher;
        recomData.mRecom = "编辑推荐";
        recomData.mMore = "more";
        mBaseData.add(recomData);
        //part 4
        for (int i = 0; i < 10; i++) {
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
    public String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(STRING_NAME.charAt(number));
        }
        return sb.toString();
    }

    public String getType() {
        Random random = new Random();
        String type = mType[random.nextInt(7)];
        return type;
    }

    public String getLevel() {
        Random random = new Random();
        String level = mLevel[random.nextInt(5)];
        return level;
    }

    public int getNum(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }
}
