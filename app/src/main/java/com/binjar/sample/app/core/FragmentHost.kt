package com.binjar.sample.app.core

import androidx.coordinatorlayout.widget.CoordinatorLayout


interface FragmentHost {
    fun showSnackMessage(parentLayout: CoordinatorLayout, message: String)

    fun showToastMessage(message: String)

    fun hideSoftKeyboard()
}