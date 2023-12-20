package com.moneo.moneo.ui.setting.reminder

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.moneo.moneo.R
import com.moneo.moneo.data.remote.model.PrediksiItem
import com.moneo.moneo.data.remote.response.PrediksiResponse
import java.util.Random

@Suppress("DEPRECATION")
class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationData = intent.getSerializableExtra("notificationData") as? PrediksiItem

        if (notificationData != null) {
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
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notificationManager.notify(Random().nextInt(), builder.build())
        }
    }
}