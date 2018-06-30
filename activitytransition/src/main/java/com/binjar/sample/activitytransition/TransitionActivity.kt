package com.binjar.sample.activitytransition

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.binjar.sample.base.bind
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_transition.*
import com.binjar.sample.R as baseR


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
class TransitionActivity : AppCompatActivity() {

    private val toolbar by bind<Toolbar>(baseR.id.toolbar)
    private val transitionHelper by lazy {
        ActivityTransitionHelper(this@TransitionActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_activity_transition)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recognitionBtn.setOnClickListener {
            //
        }
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

    fun requestActivityUpdates() {
        transitionHelper.requestForUpdates(OnCompleteListener {
            if (it.isSuccessful) {
                Snackbar.make(rootLayout, getString(R.string.activity_updates_enabled), Snackbar.LENGTH_SHORT).show()
            }
        })
        /*transitionHelper.requestForUpdates({ avoid ->
            val notification = notificationHelper.getAutoTrackingNotification()
            notificationHelper.push(NotificationHelper.AUTO_TRACKING_NOTIFICATION_ID, notification)
            PreferenceHelper.setTransitionRequestedState(this@MainActivity, true)
            setButtonsEnabledState()
            Toast.makeText(this@MainActivity, getString(R.string.activity_updates_enabled), Toast.LENGTH_SHORT).show()
        }, { e ->
            e.printStackTrace()
            PreferenceHelper.setTransitionRequestedState(this@MainActivity, false)
            setButtonsEnabledState()
            Toast.makeText(this@MainActivity, getString(R.string.activity_updates_not_enabled), Toast.LENGTH_SHORT).show()
        })*/
    }
}