package com.binjar.sample.activitytransition

import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionRequest
import com.google.android.gms.location.DetectedActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.ArrayList


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
class ActivityTransitionHelper(private val activity: AppCompatActivity) {
    companion object {
        private const val REQ_CODE = 7
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

    fun removeUpdates(successListener: OnSuccessListener<Void>, failureListener: OnFailureListener) {
        val task = ActivityRecognition.getClient(activity).removeActivityTransitionUpdates(getPendingIntent())
        task.addOnSuccessListener(successListener)
        task.addOnFailureListener(failureListener)
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(activity, TransitionUpdatesReceiver::class.java)
        intent.action = TransitionUpdatesReceiver.ACTION_ACTIVITY_RECOGNIZED
        return PendingIntent.getBroadcast(activity, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}