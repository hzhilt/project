package com.meizu.flyme.calendar.subcription_new.recommend.response;

/**
 * Created by huangzhihao on 16-8-22.
 */
public class VedioInfo {

    private String img;

    private int itemId;

    private int itemType;

    private String name;

    private String label;

    private long showDate;

    private String showDateStr;

    private int subscribeCount;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public void copy(Datas object) {
        setItemType(object.getItemType());
        setItemId(object.getItemId());
        setShowDate(object.getShowDate());
        setShowDateStr(object.getShowDateStr());
        setSubscribeCount(object.getSubscribeCount());
        setImg(object.getImg());
        setLabel(object.getLabel());
        setName(object.getName());
    }
}
