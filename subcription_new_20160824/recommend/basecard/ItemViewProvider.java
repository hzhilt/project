package com.meizu.flyme.calendar.subcription_new.recommend.basecard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ItemViewProvider<T extends ItemContent> {

    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup parent);

    protected abstract void onBindView(View view, T t, TypeItem typeItem);


    public final void onBindView(View view, TypeItem data) {
        this.onBindView(view, (T) data.content, data);
    }


    public static class ViewHolder {
        final View itemView;


        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;
        }
    }
}
