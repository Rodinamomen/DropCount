package com.example.stayhydratedapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.stayhydratedapp.NotificationManger.Companion
import com.example.stayhydratedapp.tracker.view.TrackerFragment

class NonDisruptiveNotificationManager(val context: Context) {
    companion object {
        const val CHANNEL_ID = "WATER_REMINDER_CHANNEL"
        const val NOTIFICATION_ID= 2
    }
    val sharedPreferences = context.getSharedPreferences(TrackerFragment.KEY, Context.MODE_PRIVATE)
    val totalWaterDrank = sharedPreferences.getString("total", "0")?.toInt() ?: 0
    private fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.water_reminder_channel_name)
            val importance = NotificationManager.IMPORTANCE_LOW
            val waterReminderChannel = NotificationChannel(NotificationManger.CHANNEL_ID, name, importance)
            waterReminderChannel.description = context.getString(R.string.water_reminder_channel_description)
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(waterReminderChannel)
        }
    }
    private fun createNotification(){
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(context, NotificationManger.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_notification_round)
            .setContentTitle("Your Water Intake")
            .setContentText("Great job! You've had $totalWaterDrank ml of water today. Stay hydrated and keep going!")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(null)
            .setVibrate(null)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NotificationManger.NOTIFICATION_ID, builder.build())
    }

}