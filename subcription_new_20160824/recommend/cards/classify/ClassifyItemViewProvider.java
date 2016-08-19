package com.meizu.flyme.calendar.subcription_new.recommend.cards.classify;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ItemViewProvider;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.TypeItem;
import com.meizu.flyme.calendar.subscription.Logger;

/**
 * Created by huangzhihao on 16-8-8.
 */
public class ClassifyItemViewProvider extends ItemViewProvider<ClassifyItemContent> {

    private class ViewHolder extends ItemViewProvider.ViewHolder {

        private GridView mGridView;

        public ViewHolder(View itemView) {
            super(itemView);
            mGridView = (GridView) itemView.findViewById(R.id.classify_item_layout);
        }
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {

        View root = inflater.inflate(R.layout.subscribe_new_classify_layout, parent, false);
        ViewHolder holder = new ViewHolder(root);
        root.setTag(holder);
        return root;
    }

    @Override
    protected void onBindView(View view, ClassifyItemContent classifyItemContent, final TypeItem typeItem) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mGridView.setAdapter(classifyItemContent.simpleAdapter);
    }
}
