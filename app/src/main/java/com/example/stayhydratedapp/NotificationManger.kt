package com.example.stayhydratedapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationManger(context: Context, workMangerParameters:WorkerParameters ):Worker(context,workMangerParameters) {
    companion object {
        const val CHANNEL_ID = "WATER_REMINDER_CHANNEL"
        const val NOTIFICATION_ID= 1
    }
    override fun doWork(): Result {
        scheduleNotification()
        return Result.success()
    }
    private fun scheduleNotification(){
        createChannel()
        createNotification()
    }

    private fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.water_reminder_channel_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val waterReminderChannel = NotificationChannel(CHANNEL_ID, name, importance)
            waterReminderChannel.description = applicationContext.getString(R.string.water_reminder_channel_description)
            val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(waterReminderChannel)
        }
    }
    private fun createNotification(){
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_notification_round)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(applicationContext.getString(R.string.notification_content_text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
    private fun deleteNotificationChannel(id:String){
        //can use android annotation
        // to delete channel if wanted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.deleteNotificationChannel(id)
        }
    }
}