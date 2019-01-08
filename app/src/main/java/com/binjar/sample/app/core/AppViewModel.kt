package com.binjar.sample.app.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class AppViewModel : ViewModel() {

    val loader: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    val snackMessage: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }

    val toastMessage: SingleLiveEvent<Int> by lazy { SingleLiveEvent<Int>() }

    abstract fun refresh()
}
