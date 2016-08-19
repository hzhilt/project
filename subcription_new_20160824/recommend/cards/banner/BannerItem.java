package com.meizu.flyme.calendar.subcription_new.recommend.cards.banner;

import com.meizu.flyme.calendar.subcription_new.recommend.response.Info;

/**
 * Created by huangzhihao on 16-8-5.
 */
public class BannerItem {

    private String dftTarget;

    private String img;

    private String target;

    private int linkType;

    private int linkId;

    private String title;

    public String getDftTarget() {
        return dftTarget;
    }

    public void setDftTarget(String dftTarget) {
        this.dftTarget = dftTarget;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void copy(Info object) {
        setImg(object.getImg());
        setDftTarget(object.getDftTarget());
        setLinkId(object.getLinkId());
        setLinkType(object.getLinkType());
        setTarget(object.getTarget());
        setTitle(object.getTitle());
    }
}
