package com.tarento.androidoreonotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
/**
 * @author randhirgupta
 *  @since 7/11/17.
 */
public class NotificationHelper(base: Context) : ContextWrapper(base) {

    val CHANNEL_ONE_ID = "com.tarento.androidoreonotification.ONE"
    val CHANNEL_ONE_NAME = "Channel One"

    val CHANNEL_TWO_ID = "com.tarento.androidoreonotification.TWO"
    val CHANNEL_TWO_NAME = "Channel Two"

    private var notifManager: NotificationManager? = null

    init {
        createChannels()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createChannels() {

        val notificationChannel = NotificationChannel(CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.setShowBadge(true)
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        getManager().createNotificationChannel(notificationChannel)

        val notificationChannel2 = NotificationChannel(CHANNEL_TWO_ID,
                CHANNEL_TWO_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel2.enableLights(false)
        notificationChannel2.enableVibration(true)
        notificationChannel2.lightColor = Color.RED
        notificationChannel2.setShowBadge(false)
        getManager().createNotificationChannel(notificationChannel2)

    }

    //Notification for channel 1
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getNotificationForChannel1(title: String, body: String): Notification.Builder {
        return Notification.Builder(applicationContext, CHANNEL_ONE_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.warning)
                .setAutoCancel(true)
    }


    //Notification for channel 2
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getNotificationForChannel2(title: String, body: String): Notification.Builder {
        return Notification.Builder(applicationContext, CHANNEL_TWO_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.alert)
                .setAutoCancel(true)
    }


    fun notify(id: Int, notification: Notification.Builder) {
        getManager().notify(id, notification.build())
    }


    //Send your notifications to the NotificationManager system service
    private fun getManager(): NotificationManager {
        if (notifManager == null) {
            notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notifManager as NotificationManager
    }
}