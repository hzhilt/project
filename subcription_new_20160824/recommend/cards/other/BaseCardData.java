package com.meizu.flyme.calendar.subcription_new.recommend.cards.other;

import java.util.List;

/**
 * Created by huangzhihao on 16-8-18.
 */
public class BaseCardData<T> {

    List<T> datas;
    int id;
    int template;
    String title;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
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
