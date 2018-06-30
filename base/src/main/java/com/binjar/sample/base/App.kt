package com.binjar.sample.base

import android.app.Application


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(AppLifecycleHandler())
    }
}