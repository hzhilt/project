package com.meizu.flyme.calendar.subcription_new.recommend.cards.movie;

/**
 * Created by huangzhihao on 16-8-8.
 */
public class Movie {

    static final int TYPE1 = 1;
    static final int TYPE2 = 2;

    private String imgUrl;
    private int counts;
    private String name;
    private String releaseTime;
    private String actionUrl;
    private String description;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getCounts() {
        return counts;
    }

    public String getName() {
        return name;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
