package com.binjar.sample.base

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.binjar.sample.R
import java.util.*


/**
 * Created by Arif Islam
 * Software Engineer, Brain Station-23
 * https://www.linkedin.com/in/arifnislam/
 */
class Pusher(private val appContext: Context) {
    companion object {
        const val DEFAULT_NOTIFICATION_ID = 1
        const val ACTIVITY_RECOGNITION_NOTIFICATION_ID = 2025987
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createChannel(id: String = appContext.getString(R.string.notification_default_channel_id),
                      name: CharSequence = appContext.getString(R.string.notification_default_channel_name),
                      importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
                      lockScreenVisibility: Int = Notification.VISIBILITY_PUBLIC) {
        val channel = NotificationChannel(id, name, importance)
        channel.lockscreenVisibility = lockScreenVisibility

        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)
    }

    fun push(id: Int = DEFAULT_NOTIFICATION_ID, notification: Notification) {
        val notificationManager = NotificationManagerCompat.from(appContext)
        val notificationId = if (id == -1) Math.abs(Random().nextInt(100)) else id
        notificationManager.notify(notificationId, notification)
    }

    fun cancel(id: Int) {
        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.cancel(id)
    }

    fun buildNotification(channel: String = appContext.getString(R.string.notification_default_channel_id),
                          title: String = "Hello",
                          body: String = "Notification from android samples") : Notification {
        val notificationBuilder = NotificationCompat.Builder(appContext, channel)
        notificationBuilder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        return notificationBuilder.build()
    }
}