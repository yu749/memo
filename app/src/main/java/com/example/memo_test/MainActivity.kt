package com.example.memo_test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import io.realm.Realm
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MemoListAdapter
    private lateinit var realm: Realm
    private lateinit var alert: AlertDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MemoListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.memo_list)
        recyclerView.adapter = adapter

        val editText = findViewById<EditText>(R.id.memo_edit_text)
        val addButton = findViewById<Button>(R.id.add_button)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        realm = Realm.getDefaultInstance()

        addButton.setOnClickListener {

            val text = editText.text.toString()
            if (text.isEmpty()) {
                // テキストが空の場合には無視
                return@setOnClickListener
            }
            // Realmのトランザクション
            realm.executeTransaction {
                // Memoのオブジェクトを作成
                val memo = it.createObject(Memo::class.java)
                // nameに入力してあったtextを代入
                memo.name = text
                // 上書きする
                it.copyFromRealm(memo)
            }
            refresh()
            // テキストを空にする
            editText.text.clear()
        }

        adapter.setOnItemClickListener(object:MemoListAdapter.OnItemClickListener{
            override fun onItemClickListener(view: View, position: Int, memoText: String, itemCount: Int){
                //realmの削除
                val target = realm.where(Memo::class.java).equalTo("name", memoText).findFirst()
                realm.executeTransaction {
                    if (target != null) {
                        target.deleteFromRealm()
                    }
                }
                //recyclerViewの削除
                refresh()
            }
        })
        refresh()
    }
    private fun refresh() {
        val list = realm.copyFromRealm(realm.where(Memo::class.java).findAll()).sortedBy { it.name }
        adapter.memoList = list
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu:Menu): Boolean {
        //ヘッダ用のレイアウトファイルと紐づける
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)    /// 表示されるアイコン
                .setContentTitle("ハローkotlin!!")                  /// 通知タイトル
                .setContentText("今日も1日がんばるぞい!")           /// 通知コンテンツ
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)   /// 通知の優先度

        var notificationId = 0
        val id = item.itemId;
        //各ボタンを押した時の処理
        if (id == R.id.action_settings) {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
                notificationId += 1
            }

            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }


// 通知機能

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
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    // 通知の中身
//    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_background)    /// 表示されるアイコン
//            .setContentTitle("ハローkotlin!!")                  /// 通知タイトル
//            .setContentText("今日も1日がんばるぞい!")           /// 通知コンテンツ
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)   /// 通知の優先度

//    var notificationId = 0
}


