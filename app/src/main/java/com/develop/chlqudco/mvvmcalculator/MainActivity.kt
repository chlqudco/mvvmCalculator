package com.develop.chlqudco.mvvmcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //다크모드 금지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun clearButtonClicked(view: View) {}
    fun buttonClicked(view: View) {}
    fun historyButtonClicked(view: View) {}
    fun resultButtonClicked(view: View) {}
    fun historyClearButtonClicked(view: View) {}
    fun closeHistoryButtonClicked(view: View) {}
}