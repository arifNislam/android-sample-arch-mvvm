package com.arifnislam.starter;


import com.arifnislam.droidplate.utils.AppUtil;

/**
 * Created by Arif Islam
 * https://arifnislam.github.io/
 */

public class Product {
    private long id;
    private String name;
    
    public Product(long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public long getId() {
        return id;
    }
    
    public String getName() {
        return AppUtil.nonEmpty(name);
    }
}
