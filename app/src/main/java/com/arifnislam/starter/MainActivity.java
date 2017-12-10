package com.arifnislam.starter;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arifnislam.droidplate.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Arif Islam
 * https://arifnislam.github.io/
 */

public class MainActivity extends BaseActivity {
    
    private final String[] CODE = {"ST", "AP", "TP", "BP", "TR", "NT", "DT", "SR", "GL", "PL", "DL", "GT"};
    
    RecyclerView productsView;
    CoordinatorLayout rootLayout;
    
    ProductAdapter productAdapter;
    
    Random random = new Random();
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        productsView = findViewById(R.id.productsView);
        rootLayout = findViewById(R.id.rootLayout);
        
        productsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        
        productAdapter = new ProductAdapter();
        productAdapter.setOnItemPressListener(product -> Snackbar.make(rootLayout, "Pressed item: " + product.getName(), Snackbar.LENGTH_SHORT).show());
        productsView.setAdapter(productAdapter);
        
        addItems();
    }
    
    private void addItems() {
        List<Product> products = new ArrayList<>();
        
        for (int count = 0; count < 56; count++) {
            products.add(getProduct(count));
        }
        productAdapter.replaceItems(products);
    }
    
    private Product getProduct(long id) {
        String name = String.format(Locale.getDefault(),"%s %s-%03d",
                "Stylish T-shirt", CODE[random.nextInt(CODE.length - 1)], random.nextInt(100));
        return new Product(id, name);
    }
}
