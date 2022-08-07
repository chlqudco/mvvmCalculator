package com.develop.chlqudco.mvvmcalculator.domain.history

import com.develop.chlqudco.mvvmcalculator.data.repository.HistoryRepository
import com.develop.chlqudco.mvvmcalculator.domain.Usecase

class DeleteAllHistoryUseCase(
    val historyRepository: HistoryRepository
): Usecase {

    suspend operator fun invoke(){
        historyRepository.removeAllHistory()
    }
}