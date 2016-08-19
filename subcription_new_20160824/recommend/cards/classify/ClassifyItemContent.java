package com.meizu.flyme.calendar.subcription_new.recommend.cards.classify;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ItemContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhihao on 16-8-8.
 */
public class ClassifyItemContent implements ItemContent {

    ClassifyGridViewAdapter simpleAdapter;
//    private int[] icon;
//    private String[] iconName;
//    public String[] actions;

    private List<Classify> data_list;

    public ClassifyItemContent(Context context,List<Classify> classifies) {
        //if (simpleAdapter == null) {
            //icon = new int[]{R.drawable.ic_football, R.drawable.ic_nba, R.drawable.ic_movie, R.drawable.ic_show,};
            //iconName = new String[]{"欧洲杯", "日历", "电影", "综艺",};
            //actions = new String[]{"http://www.baidu.com", "http://www.meizu.com", "http://www.qq.com", "http://www.360.com",};
            //data_list = new ArrayList<>();
            //data_list = getData();
        data_list = classifies;
        simpleAdapter = new ClassifyGridViewAdapter(context, data_list);
       // }

        this.simpleAdapter = simpleAdapter;
    }

//    public List<Map<String, Object>> getData(){
//        //cion和iconName的长度是相同的，这里任选其一都可以
//        for(int i=0;i<icon.length;i++){
//            Map<String, Object> map = new HashMap<>();
//            map.put("image", icon[i]);
//            map.put("text", iconName[i]);
//            map.put("url", actions[i]);
//            data_list.add(map);
//        }
//
//        return data_list;
//    }
}
