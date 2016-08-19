package com.meizu.flyme.calendar.subcription_new.recommend.head;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.dateview.event.BoldLineView;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItem;
import com.meizu.flyme.calendar.subcription_new.assemblyadapter.AssemblyRecyclerItemFactory;

/**
 * Created by huangzhihao on 16-8-23.
 */
public class HeadItemFactory extends AssemblyRecyclerItemFactory {


    @Override
    public boolean isTarget(Object itemObject) {
        return itemObject instanceof CardHead;
    }

    @Override
    public AssemblyRecyclerItem createAssemblyItem(ViewGroup parent) {
        return new HeadItem(R.layout.mz_group_header,parent);
    }

    public class HeadItem extends AssemblyRecyclerItem<CardHead> {


        BoldLineView blv;
        TextView titleView;
        TextView more;


        public HeadItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {
            blv = (BoldLineView) itemView.findViewById(R.id.bold_line);
            titleView = (TextView) itemView.findViewById(R.id.text1);
            more = (TextView) itemView.findViewById(R.id.sub_title);
        }

        @Override
        protected void onConfigViews(Context context) {

        }

        @Override
        protected void onSetData(int position, CardHead cardHead) {
            int color = itemView.getContext().getResources().getColor(R.color.tomato_color);
            blv.setColor(color);
            titleView.setText(cardHead.getSubTitle());
            more.setText("more");
        }
    }
}
