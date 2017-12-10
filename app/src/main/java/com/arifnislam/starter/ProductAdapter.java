package com.arifnislam.starter;


import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arifnislam.droidplate.widget.Adapter;
import com.arifnislam.droidplate.widget.ViewHolder;

/**
 * Created by Arif Islam
 * https://arifnislam.github.io/
 */

public class ProductAdapter extends Adapter<Product> {
    
    @Override public ViewHolder<Product> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        ViewHolder<Product> productViewHolder = new ProductHolder(view);
        productViewHolder.itemView.setOnClickListener(v -> {
            if (itemPressListener != null) {
                itemPressListener.onItemPressed(dataset.get(productViewHolder.getAdapterPosition()));
            }
        });
        return productViewHolder;
    }
    
    class ProductHolder extends ViewHolder<Product> {
        AppCompatTextView nameView;
        
        ProductHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.productName);
        }
    
        @Override public void bindTo(Product product) {
            nameView.setText(product.getName());
        }
    }
}
