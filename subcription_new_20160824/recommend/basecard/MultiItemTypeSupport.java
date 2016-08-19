package com.meizu.flyme.calendar.subcription_new.recommend.basecard;

/**
 * Created by huangzhihao on 16-8-11.
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}
