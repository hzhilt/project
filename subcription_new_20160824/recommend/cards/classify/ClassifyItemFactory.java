package com.meizu.flyme.calendar.subcription_new.recommend.cards.classify;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridView;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItem;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItemFactory;

/**
 * Created by huangzhihao on 16-8-23.
 */
public class ClassifyItemFactory extends AssemblyRecyclerItemFactory<ClassifyItemFactory.ClassifyItem> {


    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof ClassifyList;
    }

    @Override
    public ClassifyItem createAssemblyItem(ViewGroup parent) {
        return new ClassifyItem(R.layout.subscribe_new_classify_layout,parent);
    }

    public class ClassifyItem extends AssemblyRecyclerItem<ClassifyList> {

        GridView gridView;

        public ClassifyItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {
            gridView = (GridView) findViewById(R.id.classify_item_layout);
        }

        @Override
        protected void onConfigViews(Context context) {


        }

        @Override
        protected void onSetData(int position, ClassifyList classifyList) {

            ClassifyGridViewAdapter simpleAdapter = new ClassifyGridViewAdapter(gridView.getContext(), classifyList.getmList());
            gridView.setAdapter(simpleAdapter);

        }
    }
}
