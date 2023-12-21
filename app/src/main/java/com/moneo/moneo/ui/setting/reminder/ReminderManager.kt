package com.moneo.moneo.ui.setting.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.moneo.moneo.R
import com.moneo.moneo.data.remote.response.Perbandingan
import java.util.Calendar
import java.util.Random

class ReminderManager(private val context: Context)  {

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleRandomNotifications(notificationData: Perbandingan) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notificationTimes = getRandomNotificationTimes()
            for (time in notificationTimes) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, ReminderReceiver::class.java)
                intent.putExtra("notificationData", notificationData)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationData.hashCode(), // Use a unique request code for each pending intent
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)

                alarmManager.cancel(pendingIntent)

                // Schedule Notification
                scheduleNotification(time, notificationData)
            }
        }
    }

    private fun getRandomNotificationTimes(): List<Long> {
        val random = Random()
        val notificationTimes = mutableListOf<Long>()

        for (i in 1..3) {
            val hour = random.nextInt(24)
            val minute = random.nextInt(60)
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            notificationTimes.add(calendar.timeInMillis)
        }

        return notificationTimes
    }

    @SuppressLint("ServiceCast")
    private fun scheduleNotification(timeInMillis: Long, notificationData: Perbandingan) {
        Log.d("Notification", "Scheduling notification at $timeInMillis")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.getString(R.string.notification_channel_id),
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val title = "Daily Prediction"
        val content = "Total Income: ${notificationData.totalPemasukanIDR} IDR"

        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, builder.build())
    }
}