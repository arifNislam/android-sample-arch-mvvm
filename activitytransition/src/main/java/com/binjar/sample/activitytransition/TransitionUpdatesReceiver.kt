package com.binjar.sample.activitytransition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.binjar.sample.base.Pusher
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
class TransitionUpdatesReceiver : BroadcastReceiver() {
    companion object {
        const val ACTION_ACTIVITY_RECOGNIZED = "ACTIVITY_RECOGNIZED"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.apply {
            if (ActivityTransitionResult.hasResult(intent)) {
                val result = ActivityTransitionResult.extractResult(intent)
                result?.apply {
                    val appContext = context.applicationContext
                    for (event in this.transitionEvents) {
                        val pusher = Pusher(appContext)
                        val text = when (event.activityType) {
                            DetectedActivity.STILL -> "User is still"
                            DetectedActivity.WALKING -> "User is walking"
                            DetectedActivity.RUNNING -> "User is running"
                            DetectedActivity.ON_BICYCLE -> "User riding a bicycle"
                            DetectedActivity.IN_VEHICLE -> "User is in vehicle"
                            else -> "Unknown activity"
                        }

                        val notification = pusher.buildNotification(appContext.getString(R.string.notification_default_channel_id),
                                "User's activity changed", text)
                        pusher.push(-1, notification)
                    }
                }
            }
        }
    }
}