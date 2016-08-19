package com.meizu.flyme.calendar.subcription_new.recommend.basecard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import flyme.support.v7.widget.MzRecyclerView;


public class TypeItemsAdapter extends MzRecyclerView.Adapter<TypeItemsAdapter.ViewHolder> {

    private final List<TypeItem> typeItems;


    public TypeItemsAdapter(List<TypeItem> typeItems) {this.typeItems = typeItems;}


    @Override public int getItemViewType(int position) {
        ItemContent content = typeItems.get(position).content;
        int index = ItemTypePool.getContents().indexOf(content.getClass());
        return index;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int indexViewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = ItemTypePool.getProviderByIndex(indexViewType).onCreateView(inflater, parent);
        ViewHolder holder = new ViewHolder(root);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        TypeItem typeItem = typeItems.get(position);
        ItemTypePool.getProviderByIndex(type).onBindView(holder.itemView, typeItem);
    }


    @Override public int getItemCount() {
        return typeItems.size();
    }


    static class ViewHolder extends MzRecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}