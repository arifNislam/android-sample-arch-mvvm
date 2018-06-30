package com.binjar.sample.base

import android.app.Activity
import android.app.Application
import android.os.Bundle


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
class AppLifecycleHandler : Application.ActivityLifecycleCallbacks {

    companion object {
        var isVisible = true
    }

    private var resumed: Int = 0
    private var paused: Int = 0
    private var started: Int = 0
    private var stopped: Int = 0

    override fun onActivityPaused(activity: Activity?) {
        ++paused
    }

    override fun onActivityResumed(activity: Activity?) {
        ++resumed
    }

    override fun onActivityStarted(activity: Activity?) {
        ++started
    }

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {
        if (activity !is AuthGuard) {
            ++stopped
        }
        isVisible = started > stopped

        if (activity is AuthGuard) {
            ++stopped
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }
}