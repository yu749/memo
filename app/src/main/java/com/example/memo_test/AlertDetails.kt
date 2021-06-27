package com.example.memo_test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class AlertDetails  {

//    val textTitle = getString(R.string.app_name)
//    private val CHANNEL_ID = "default"
//
//    // 通知のタップアクションを追加
//    val intent = Intent(this, AlertDetails::class.java).apply {
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    }
//    // タップアクション追加に必要なオブジェクト
//    val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//    // 通知コンテンツ設定
//    var builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.notification_icon)
//            .setContentTitle("memo_test")
//            .setContenttext(textContent)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            // 通知タップアクションに必要なオブジェクトをセットする
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//    // チャネル作成と重要度の設定
//    private fun createNotificationChannel() {
//        // Android7.1以下との互換性を保つため
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.channel_name)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val descriptionText = getString(R.string.channel_description)
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                    description = descriptionText
//            }
//            // 通知チャネルをシステムに登録
//            val notificationManager: NotificationManager =
//                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                    notificationManager.createNotificationChannel(channel)
//        }
//    }


    // 通知を表示
//    with(NotificationManagerCompat.from(this)) {
//        notify(notificationId, builder.build())
//    }

}