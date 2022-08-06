package com.develop.chlqudco.mvvmcalculator.presentation.main

import android.view.View
import com.develop.chlqudco.mvvmcalculator.databinding.ActivityMainBinding
import com.develop.chlqudco.mvvmcalculator.presentation.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel by viewModel<MainViewModel>()
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    fun clearButtonClicked(view: View) {}
    fun buttonClicked(view: View) {}
    fun historyButtonClicked(view: View) {}
    fun resultButtonClicked(view: View) {}
    fun historyClearButtonClicked(view: View) {}
    fun closeHistoryButtonClicked(view: View) {}
}