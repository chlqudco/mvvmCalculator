package com.develop.chlqudco.mvvmcalculator.data.repository

import com.develop.chlqudco.mvvmcalculator.data.entity.HistoryEntity

interface HistoryRepository {

    suspend fun getAllHistory(): List<HistoryEntity>

    suspend fun removeAllHistory()

    suspend fun insertHistory(historyEntity: HistoryEntity)
}