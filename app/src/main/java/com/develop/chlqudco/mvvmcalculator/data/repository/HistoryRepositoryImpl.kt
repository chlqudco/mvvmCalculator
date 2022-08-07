package com.develop.chlqudco.mvvmcalculator.data.repository

import com.develop.chlqudco.mvvmcalculator.data.dao.HistoryDao
import com.develop.chlqudco.mvvmcalculator.data.entity.HistoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class HistoryRepositoryImpl(
    private val historyDao: HistoryDao,
    private val ioDispatcher: CoroutineDispatcher
): HistoryRepository {

    override suspend fun getAllHistory(): List<HistoryEntity> = withContext(ioDispatcher) {
        historyDao.getAllHistory()
    }

    override suspend fun removeAllHistory() = withContext(ioDispatcher) {
        historyDao.deleteAllHistory()
    }

    override suspend fun insertHistory(historyEntity: HistoryEntity) = withContext(ioDispatcher) {
        historyDao.insertHistory(historyEntity)
    }
}