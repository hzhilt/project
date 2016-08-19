package com.meizu.flyme.calendar.subcription_new.recommend.cards.classify;

import com.meizu.flyme.calendar.subcription_new.recommend.response.Info;

/**
 * Created by huangzhihao on 16-8-10.
 */
public class Classify {

    private String icon;

    private int id;

    private String img;

    private String name;

    private int template;

    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setImg(String img){
        this.img = img;
    }
    public String getImg(){
        return this.img;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setTemplate(int template){
        this.template = template;
    }
    public int getTemplate(){
        return this.template;
    }

    public void copy(Info object) {
        setImg(object.getImg());
        setIcon(object.getDftTarget());
        setId(object.getId());
        setName(object.getName());
        setTemplate(object.getTemplate());
    }
}
