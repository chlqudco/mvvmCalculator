package com.develop.chlqudco.mvvmcalculator.domain.history

import com.develop.chlqudco.mvvmcalculator.data.entity.HistoryEntity
import com.develop.chlqudco.mvvmcalculator.data.repository.HistoryRepository
import com.develop.chlqudco.mvvmcalculator.domain.Usecase

class GetAllHistoryUseCase(
    val historyRepository: HistoryRepository
): Usecase {

    suspend operator fun invoke(): List<HistoryEntity>{
        return historyRepository.getAllHistory()
    }
}