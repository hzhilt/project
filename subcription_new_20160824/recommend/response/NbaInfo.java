package com.meizu.flyme.calendar.subcription_new.recommend.response;

import java.util.HashMap;
import java.util.List;

/**
 * Created by huangzhihao on 16-8-22.
 */
public class NbaInfo {

    private String img1;

    private String img2;

    private int itemId;

    private int itemType;

    private String title1;

    private String title2;

    private String label;

    private long showDate;

    private String showDateStr;

    private int subscribeCount;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getShowDate() {
        return showDate;
    }

    public void setShowDate(long showDate) {
        this.showDate = showDate;
    }

    public String getShowDateStr() {
        return showDateStr;
    }

    public void setShowDateStr(String showDateStr) {
        this.showDateStr = showDateStr;
    }

    public int getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(int subscribeCount) {
        this.subscribeCount = subscribeCount;
    }



    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public void copy(Datas object) {
        setTitle1(object.getTitle1());
        setTitle2(object.getTitle2());
        setSubscribeCount(object.getSubscribeCount());
        setShowDateStr(object.getShowDateStr());
        setShowDate(object.getShowDate());
        setImg1(object.getImg1());
        setImg2(object.getImg2());
        setItemId(object.getItemId());
        setItemType(object.getItemType());
        setLabel(object.getLabel());
    }
}
