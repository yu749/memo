package com.example.memo_test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class AlertDetails: BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"Received", Toast.LENGTH_LONG).show()

        val text = intent.getStringExtra(MainActivity.EXTRA_TEXT) ?: "データがありません！"

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()

        deliverNotification(context, text)
    }

    // 通知を指定した時間に返す
    private fun deliverNotification(context: Context, text: String) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(context, 0, contentIntent, 0)

        val title = context.getString(R.string.notification_title)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)       /// 表示されるアイコン
                        .setContentTitle(title)                           /// 通知タイトル
                        .setContentText(text)                             /// 通知コンテンツ
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) /// 通知の優先度
                        .setContentIntent(contentPendingIntent)           /// 通知をタップした際のIntent
                        .setAutoCancel(true)

        var notificationId = 0
                with(NotificationManagerCompat.from(context)) {
                    notify(notificationId, builder.build())
                    notificationId += 1
                }
    }

    val CHANNEL_ID = "channel_id"
    val channel_name = "channel_name"
    val channel_description = "channel_description "

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channel_name
            val descriptionText = channel_description
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            /// チャネルを登録
//            val notificationManager: NotificationManager =
//                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}