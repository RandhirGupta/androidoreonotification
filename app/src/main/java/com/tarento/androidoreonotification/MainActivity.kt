package com.tarento.androidoreonotification

import android.app.Notification
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val notification_one: Int = 101
    private val notification_two: Int = 102
    var mainUI: MainUi? = null

    val CHANNEL_ONE_ID = "com.tarento.androidoreonotification.ONE"

    val CHANNEL_TWO_ID = "com.tarento.androidoreonotification.TWO"

    private var notificationHelper: NotificationHelper? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationHelper = NotificationHelper(this)
        mainUI = MainUi(findViewById(R.id.activity_main));
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun postNotification(id: Int, title: String) {
        var notificationBuilder: Notification.Builder? = null
        when (id) {
            notification_one -> notificationBuilder = notificationHelper!!.getNotificationForChannel1(title,
                    getString(R.string.channel_one_body))

            notification_two -> notificationBuilder = notificationHelper!!.getNotificationForChannel2(title,
                    getString(R.string.channel_two_body))
        }

        if (notificationBuilder != null) {
            notificationHelper!!.notify(id, notificationBuilder)
        }
    }

    fun goToNotificationSettings(channel: String) {
        val i = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel)
        startActivity(i)
    }

    inner class MainUi (root: View) : View.OnClickListener {

        val editTextOne: EditText
        val editTextTwo: EditText

        val buttonOne: Button
        val buttonOne1: Button

        val buttonTwo: Button
        val buttonTwo2: Button

        init {

            editTextOne = root.findViewById(R.id.channel_one_text)

            buttonOne = root.findViewById(R.id.post_to_channel_one)
            buttonOne.setOnClickListener(this)

            buttonOne1 = root.findViewById(R.id.channel_one_settings)
            buttonOne1.setOnClickListener(this)

            editTextTwo = root.findViewById(R.id.channel_two_text)

            buttonTwo = root.findViewById(R.id.post_to_channel_two)
            buttonTwo.setOnClickListener(this)

            buttonTwo2 = root.findViewById(R.id.channel_two_settings)
            buttonTwo2.setOnClickListener(this)
        }


        private fun getChannelOneText(): String {
            return if (editTextOne != null) {
                editTextOne!!.getText().toString()
            } else ""
        }

        private fun getChannelTwoText(): String {
            return if (editTextOne != null) {
                editTextTwo!!.getText().toString()
            } else ""
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun onClick(v: View) {
            when (v.getId()) {

                R.id.post_to_channel_one -> postNotification(notification_one, getChannelOneText())
                R.id.channel_one_settings -> goToNotificationSettings(CHANNEL_ONE_ID)
                R.id.post_to_channel_two -> postNotification(notification_two, getChannelTwoText())
                R.id.channel_two_settings -> goToNotificationSettings(CHANNEL_TWO_ID)
            }
        }
    }
}
