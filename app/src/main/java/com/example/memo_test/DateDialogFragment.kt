package com.example.memo_test

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import com.example.memo_test.MainActivity.Companion.MemoText
import java.time.Year
import java.util.*

class DateDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface OnSelectedDateListener {
        fun selectedDate(year: Int, month: Int, date: Int)
    }
    // リスナー変数
    private lateinit var listener: OnSelectedDateListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSelectedDateListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time
        val context = context // smart cast
        return when {
            context != null -> {
                DatePickerDialog(
                    context,
                    this, // ここでは DatePickerDialog の リスナーを渡す
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE))
            }
            else -> super.onCreateDialog(savedInstanceState)
        }
    }

    // DatePickerDialog から選択結果が渡される。
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener.selectedDate(year, month, dayOfMonth)
    }

    companion object {
        private val TAG = DateDialogFragment::class.java.simpleName
    }
}