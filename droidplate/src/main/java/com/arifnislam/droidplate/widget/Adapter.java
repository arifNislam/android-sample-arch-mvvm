package com.arifnislam.droidplate.widget;


import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arif Islam
 * https://arifnislam.github.io/
 */

public abstract class Adapter<Item> extends RecyclerView.Adapter<ViewHolder<Item>> {
    
    protected List<Item> dataset;
    
    protected OnItemPressListener<Item> itemPressListener;
    
    public Adapter() {
        dataset = new ArrayList<>();
    }
    
    public void setOnItemPressListener(OnItemPressListener<Item> itemPressListener) {
        this.itemPressListener = itemPressListener;
    }
    
    public void replaceItems(List<Item> items) {
        dataset.clear();
        dataset.addAll(items);
        notifyDataSetChanged();
    }
    
    @Override public void onBindViewHolder(ViewHolder<Item> holder, int position) {
        holder.bindTo(dataset.get(position));
    }
    
    @Override public int getItemCount() {
        return dataset.size();
    }
    
    public interface OnItemPressListener<Item> {
        void onItemPressed(Item item);
    }
}
