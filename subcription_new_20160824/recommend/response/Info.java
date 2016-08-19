package com.meizu.flyme.calendar.subcription_new.recommend.response;

import java.util.List;

/**
 * Created by huangzhihao on 16-8-22.
 */
public class Info {

    private String dftTarget;

    private String img;

    private String target;

    private int linkType;

    private int linkId;

    private String label;

    private long showDate;

    private String showDateStr;

    private int subscribeCount;

    private List<Datas> datas;

    private int id;

    private int template;

    private String title;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<Datas> getDatas() {
        return datas;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
