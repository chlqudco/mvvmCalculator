package com.develop.chlqudco.mvvmcalculator.di

import com.develop.chlqudco.mvvmcalculator.data.db.AppDatabase
import com.develop.chlqudco.mvvmcalculator.data.repository.HistoryRepository
import com.develop.chlqudco.mvvmcalculator.data.repository.HistoryRepositoryImpl
import com.develop.chlqudco.mvvmcalculator.domain.history.DeleteAllHistoryUseCase
import com.develop.chlqudco.mvvmcalculator.domain.history.GetAllHistoryUseCase
import com.develop.chlqudco.mvvmcalculator.domain.history.InsertHistoryUseCase
import com.develop.chlqudco.mvvmcalculator.presentation.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    //usecase
    factory { GetAllHistoryUseCase(get()) }
    factory { DeleteAllHistoryUseCase(get()) }
    factory { InsertHistoryUseCase(get()) }

    //코루틴
    single { Dispatchers.IO }
    single { Dispatchers.Main }

    //DB
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().historyDao() }

    //레포지토리
    single<HistoryRepository> { HistoryRepositoryImpl(get(),get()) }

    //뷰모델
    viewModel { MainViewModel(get(),get(),get()) }
}