package com.meizu.flyme.calendar.subcription_new.recommend.cards.classify;

import java.util.List;

/**
 * Created by huangzhihao on 16-8-15.
 */
public class Classifys {
    private int code;

    private String message;

    private String redirect;

    private List<Classify> value ;

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
    public void setValue(List<Classify> value){
        this.value = value;
    }
    public List<Classify> getValue(){
        return this.value;
    }

}
