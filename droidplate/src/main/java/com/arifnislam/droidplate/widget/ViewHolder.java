package com.arifnislam.droidplate.widget;


import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Arif Islam
 * https://arifnislam.github.io/
 */

public abstract class ViewHolder<Item> extends RecyclerView.ViewHolder {
    
    public ViewHolder(View itemView) {
        super(itemView);
    }
    
    public abstract void bindTo(Item item);
}
