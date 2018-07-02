package com.binjar.sample.activitytransition

import android.app.PendingIntent
import android.content.Intent
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionRequest
import com.google.android.gms.location.DetectedActivity
import com.google.android.gms.tasks.OnCompleteListener
import java.util.*


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
class ActivityTransitionHelper(private val activity: AppCompatActivity) {
    companion object {
        private const val REQ_CODE = 7
        const val KEY_REQUESTED = "activity_update_request"
    }

    private val transitionRequest by lazy {
        val transitions = ArrayList<ActivityTransition>()

        transitions.add(ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build())

        transitions.add(ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build())

        transitions.add(ActivityTransition.Builder()
                .setActivityType(DetectedActivity.ON_BICYCLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build())

        transitions.add(ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build())

        ActivityTransitionRequest(transitions)
    }

    fun requestForUpdates(listener: OnCompleteListener<Void>) {
        val task = ActivityRecognition.getClient(activity).requestActivityTransitionUpdates(transitionRequest, getPendingIntent())
        task.addOnCompleteListener(listener)
    }

    fun removeUpdates(listener: OnCompleteListener<Void>) {
        val task = ActivityRecognition.getClient(activity).removeActivityTransitionUpdates(getPendingIntent())
        task.addOnCompleteListener(listener)
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(activity, TransitionUpdatesReceiver::class.java)
        intent.action = TransitionUpdatesReceiver.ACTION_ACTIVITY_RECOGNIZED
        return PendingIntent.getBroadcast(activity, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun requestingTransitionUpdates(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(activity.applicationContext).getBoolean(KEY_REQUESTED, false)
    }

    fun setTransitionRequestState(requesting: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(activity.applicationContext).edit().putBoolean(KEY_REQUESTED, requesting).apply()
    }
}