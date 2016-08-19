package com.meizu.flyme.calendar.subcription_new.recommend.cards.banner;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.recommend.BannerView;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ItemViewProvider;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.TypeItem;
import com.meizu.flyme.calendar.subcription_new.show.ShowActivity;

/**
 * Created by huangzhihao on 16-8-8.
 */
public class BannerItemViewProvider extends ItemViewProvider<BannerItemContent> {

    private class ViewHolder extends ItemViewProvider.ViewHolder {

        private BannerView mBannerView;

        public ViewHolder(View itemView) {
            super(itemView);
            mBannerView = (BannerView) itemView.findViewById(R.id.banner);
        }
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent) {

        View root = inflater.inflate(R.layout.header_banner, parent, false);
        ViewHolder viewHolder = new ViewHolder(root);
        root.setTag(viewHolder);
        return root;
    }

    @Override
    protected void onBindView(final View view, final BannerItemContent bannerItemContent, TypeItem typeItem) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.mBannerView.setupItems(bannerItemContent.mItem.getValue());
        holder.mBannerView.setOnBannerItemClickListener(new BannerView.OnBannerItemClickListener() {
            @Override
            public void onItemClick(BannerItem item, int position) {
                try {
//                    String target = bannerItemContent.mItem.getValue().get(position).getTarget();
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(target));
//                    view.getContext().startActivity(intent);
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), ShowActivity.class);
                    view.getContext().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
