package com.example.memo_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MemoListAdapter
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MemoListAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.memo_list)
        recyclerView.adapter = adapter

        val editText = findViewById<EditText>(R.id.memo_edit_text)
        val addButton = findViewById<Button>(R.id.add_button)

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
            Toast.makeText(applicationContext, "追加しました", Toast.LENGTH_LONG).show()
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
}


