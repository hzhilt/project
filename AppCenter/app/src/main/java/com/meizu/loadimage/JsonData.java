package com.meizu.loadimage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-12-4.
 * 解析网络json数据
 */
public class JsonData {

    private String mJsonStr;
    private String mFirstObi;
    private String mSecondObj;

    private List<Icon> icons = new ArrayList<Icon>();

    public JsonData(String jsonStr, String firstObi, String secondObj) throws JSONException {
        this.mJsonStr = jsonStr;
        this.mFirstObi = firstObi;
        this.mSecondObj = secondObj;
    }


    public JsonData(String firstObi, String secondObj) throws JSONException {

        this.mFirstObi = firstObi;
        this.mSecondObj = secondObj;
    }


    public List<Icon> getData() throws JSONException, UnsupportedEncodingException {
        JSONObject jsonObj = new JSONObject(mJsonStr);
        if (jsonObj.has(mFirstObi)){
            JSONObject jsonObjValue= jsonObj.getJSONObject(mFirstObi);
            if (jsonObjValue.has(mSecondObj)){
                JSONArray arr = jsonObjValue.getJSONArray(mSecondObj);

                for (int i = 0; i < arr.length(); i++) {
                    Icon icon = new Icon();
                    icon.state = false;
                    icon.url = arr.getJSONObject(i).getString("appIcon");
                    icon.name = arr.getJSONObject(i).getString("appNameZhCn");
                    icons.add(icon);
                }
            }
        }
        return icons;
    }

    public List<Icon> getJsonData(JSONObject response) throws JSONException, UnsupportedEncodingException {
        if (response.has(mFirstObi)){
            JSONObject jsonObjValue= response.getJSONObject(mFirstObi);
            if (jsonObjValue.has(mSecondObj)){
                JSONArray arr = jsonObjValue.getJSONArray(mSecondObj);

                for (int i = 0; i < arr.length(); i++) {
                    Icon icon = new Icon();
                    icon.state = false;
                    icon.url = arr.getJSONObject(i).getString("appIcon");
                    icon.name = arr.getJSONObject(i).getString("appNameZhCn");
                    icons.add(icon);
                }
            }
        }
        return icons;
    }
}


