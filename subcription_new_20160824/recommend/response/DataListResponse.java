package com.meizu.flyme.calendar.subcription_new.recommend.response;

import java.util.List;

/**
 * Created by huangzhihao on 16-8-22.
 */
public class DataListResponse<E> extends DataResponse {

    private List<E> data;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}

