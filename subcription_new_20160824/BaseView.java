package com.meizu.flyme.calendar.subcription_new;

import android.content.Context;

/**
 * Created by huangzhihao on 16-8-3.
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
    Context getContext();
}
