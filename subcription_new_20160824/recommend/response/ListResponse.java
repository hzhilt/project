package com.meizu.flyme.calendar.subcription_new.recommend.response;

import java.util.List;

/**
 * Created by Sven.J on 16-8-18.
 */
public class ListResponse<E> extends BasicResponse {
    List<E> value;

    public List<E> getValue() {
        return value;
    }

    public void setValue(List<E> value) {
        this.value = value;
    }
}
