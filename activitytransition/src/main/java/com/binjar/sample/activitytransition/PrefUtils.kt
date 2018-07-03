package com.binjar.sample.activitytransition

import android.content.Context
import android.preference.PreferenceManager
import com.google.android.gms.location.DetectedActivity


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
internal class PrefUtils(private val appContext: Context) {
    companion object {
        const val KEY_REQUESTED = "activity_update_request"
        const val KEY_LAST_ACTIVITY = "last_activity"
    }

    fun requestingTransitionUpdates(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(appContext).getBoolean(KEY_REQUESTED, false)
    }

    fun saveTransitionRequestState(requesting: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(appContext).edit().putBoolean(KEY_REQUESTED, requesting).apply()
    }

    fun saveLastActivityType(activityType: Int) {
        PreferenceManager.getDefaultSharedPreferences(appContext).edit().putInt(KEY_LAST_ACTIVITY, activityType).apply()
    }

    fun getLastActivityType(): Int {
        return PreferenceManager.getDefaultSharedPreferences(appContext).getInt(KEY_LAST_ACTIVITY, DetectedActivity.UNKNOWN)
    }

    fun deleteLastActivityType() {
        PreferenceManager.getDefaultSharedPreferences(appContext).edit().remove(KEY_LAST_ACTIVITY).apply()
    }
}