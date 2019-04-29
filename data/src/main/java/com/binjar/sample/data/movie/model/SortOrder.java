package com.binjar.sample.data.movie.model;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Md Ariful Islam
 * @since Apr 28, 2019
 */

@StringDef({SortOrder.ASC, SortOrder.DESC})
@Retention(RetentionPolicy.SOURCE)
public @interface SortOrder {
    String ASC = "asc";
    String DESC = "desc";
}
