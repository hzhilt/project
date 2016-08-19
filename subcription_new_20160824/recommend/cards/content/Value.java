package com.meizu.flyme.calendar.subcription_new.recommend.cards.content;

import com.meizu.flyme.calendar.subcription_new.recommend.model.sport.Datas;

import java.util.List;

/**
 * Created by huangzhihao on 16-8-16.
 */
public class Value {
    private List<Datas> datas ;

    private int id;

    private int template;

    private String title;

    public void setDatas(List<Datas> datas){
        this.datas = datas;
    }
    public List<Datas> getDatas(){
        return this.datas;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setTemplate(int template){
        this.template = template;
    }
    public int getTemplate(){
        return this.template;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

}
