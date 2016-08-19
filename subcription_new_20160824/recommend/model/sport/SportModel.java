package com.meizu.flyme.calendar.subcription_new.recommend.model.sport;

import java.util.List;

/**
 * Created by huangzhihao on 16-8-5.
 * 体育卡片的实体类
 */
public class SportModel {
    private int code;

    private String message;

    private String redirect;

    private List<Value> value ;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setRedirect(String redirect){
        this.redirect = redirect;
    }
    public String getRedirect(){
        return this.redirect;
    }
    public void setValue(List<Value> value){
        this.value = value;
    }
    public List<Value> getValue(){
        return this.value;
    }

}
