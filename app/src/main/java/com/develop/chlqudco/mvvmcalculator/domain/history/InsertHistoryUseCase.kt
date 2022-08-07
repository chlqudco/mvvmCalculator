package com.develop.chlqudco.mvvmcalculator.domain.history

import com.develop.chlqudco.mvvmcalculator.data.dao.HistoryDao
import com.develop.chlqudco.mvvmcalculator.data.entity.HistoryEntity
import com.develop.chlqudco.mvvmcalculator.domain.Usecase

class InsertHistoryUseCase(
    val historyDao: HistoryDao
): Usecase {

    suspend operator fun invoke(historyEntity: HistoryEntity){
        historyDao.insertHistory(historyEntity)
    }
}