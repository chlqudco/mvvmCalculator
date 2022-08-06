package com.develop.chlqudco.mvvmcalculator.di

import com.develop.chlqudco.mvvmcalculator.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    viewModel { MainViewModel() }
}