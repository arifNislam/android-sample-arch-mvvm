package com.arifnislam.droidplate.utils;


import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by Arif Islam
 * https://arifnislam.github.io/
 */

public final class AppUtil {
    private AppUtil() {
    }
    
    @NonNull public static String nonEmpty(String text) {
        if (TextUtils.isEmpty(text) || text.trim().equalsIgnoreCase("null")) {
            return "";
        } else {
            return text.trim();
        }
    }
}
