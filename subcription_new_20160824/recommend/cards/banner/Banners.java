package com.meizu.flyme.calendar.subcription_new.recommend.cards.banner;

import java.util.List;

/**
 * Created by huangzhihao on 16-8-15.
 */
public class Banners {
    private int code;

    private String message;

    private String redirect;

    private List<BannerItem> value ;

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
    public void setValue(List<BannerItem> value){
        this.value = value;
    }
    public List<BannerItem> getValue(){
        return this.value;
    }

}
