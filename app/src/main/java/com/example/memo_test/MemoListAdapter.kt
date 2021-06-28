package com.example.memo_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemoListAdapter: RecyclerView.Adapter<MemoListAdapter.MemoViewHolder>() {
    lateinit var viewlistener: OnViewClickListener
    lateinit var listener: OnItemClickListener
    var memoList = listOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return MemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.memo_text_view)
        textView.text = memoList[position].name
        // リスナー
        holder.memoText.setOnClickListener {
            viewlistener.onItemClickListener(it, position, memoList[position].name, memoList.size)
        }
        holder.deleteButton.setOnClickListener {
            listener.onItemClickListener(it, position, memoList[position].name, memoList.size)
        }
    }

    override fun getItemCount(): Int = memoList.size

    class MemoViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val memoText = view.findViewById<TextView>(R.id.memo_text_view)
        val deleteButton = view.findViewById<Button>(R.id.delete_button)
    }

    interface OnViewClickListener {
        fun onItemClickListener(view: View, position: Int, memoText: String, itemCount: Int)
    }
    interface OnItemClickListener {
        fun onItemClickListener(view: View, position: Int, memoText: String, itemCount: Int)
    }

    fun setOnItemClickListener(listener: OnViewClickListener) {
        this.viewlistener = listener
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}