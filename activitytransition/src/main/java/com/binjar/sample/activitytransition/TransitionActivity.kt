package com.binjar.sample.activitytransition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.binjar.sample.base.bind
import com.google.android.gms.location.DetectedActivity
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_transition.*
import com.binjar.sample.R as baseR


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
class TransitionActivity : AppCompatActivity() {

    companion object {
        const val ACTION_TRANSITION_UPDATE = "ACTION_TRANSITION_UPDATE"
        const val KEY_ACTIVITY_TYPE = "KEY_ACTIVITY_TYPE"
    }

    private val toolbar by bind<Toolbar>(baseR.id.toolbar)
    private val transitionHelper by lazy {
        ActivityTransitionHelper(this@TransitionActivity)
    }

    private val activityTransitionReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (ACTION_TRANSITION_UPDATE.equals(action) && intent?.hasExtra(KEY_ACTIVITY_TYPE) == true) {
                updateTransitionState(intent.getIntExtra(KEY_ACTIVITY_TYPE, DetectedActivity.UNKNOWN))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_activity_transition)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recognitionBtn.isChecked = transitionHelper.requestingTransitionUpdates()

        recognitionBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestActivityUpdates()
            } else {
                removeActivityUpdates()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(activityTransitionReceiver, IntentFilter(ACTION_TRANSITION_UPDATE))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestActivityUpdates() {
        transitionHelper.requestForUpdates(OnCompleteListener {
            if (it.isSuccessful) {
                transitionHelper.setTransitionRequestState(true)
                Snackbar.make(rootLayout, getString(R.string.activity_updates_enabled), Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun removeActivityUpdates() {
        transitionHelper.removeUpdates(OnCompleteListener {
            if (it.isSuccessful) {
                transitionHelper.setTransitionRequestState(false)
                Snackbar.make(rootLayout, getString(R.string.activity_updates_disabled), Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTransitionState(activityType: Int) {
        currentActivityText.text = when(activityType) {
            DetectedActivity.STILL -> "User is still"
            DetectedActivity.WALKING -> "User is walking"
            DetectedActivity.RUNNING -> "User is running"
            DetectedActivity.ON_BICYCLE -> "User riding a bicycle"
            DetectedActivity.IN_VEHICLE -> "User is in vehicle"
            else -> "Unknown activity"
        }

        currentActivityIcon.setImageResource(when(activityType) {
            DetectedActivity.STILL -> R.drawable.ic_activity_still
            DetectedActivity.WALKING -> R.drawable.ic_activity_walk
            DetectedActivity.RUNNING -> R.drawable.ic_activity_run
            DetectedActivity.ON_BICYCLE -> R.drawable.ic_activity_bicycle
            DetectedActivity.IN_VEHICLE -> R.drawable.ic_activity_vehicle
            else -> R.drawable.ic_activity_unknown
        })
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(activityTransitionReceiver)
    }
}