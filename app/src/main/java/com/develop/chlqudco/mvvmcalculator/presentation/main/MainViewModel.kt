package com.develop.chlqudco.mvvmcalculator.presentation.main

import com.develop.chlqudco.mvvmcalculator.data.entity.HistoryEntity
import com.develop.chlqudco.mvvmcalculator.domain.history.DeleteAllHistoryUseCase
import com.develop.chlqudco.mvvmcalculator.domain.history.GetAllHistoryUseCase
import com.develop.chlqudco.mvvmcalculator.domain.history.InsertHistoryUseCase
import com.develop.chlqudco.mvvmcalculator.presentation.BaseViewModel
import java.math.BigInteger

internal class MainViewModel(
    val getAllHistoryUseCase: GetAllHistoryUseCase,
    val deleteAllHistoryUseCase: DeleteAllHistoryUseCase,
    val insertHistoryUseCase: InsertHistoryUseCase
): BaseViewModel() {

    var isOperator: Boolean = false
    var hasOperator: Boolean = false

    fun initOperator(){
        isOperator = false
        hasOperator = false
    }

    fun calculate(num1: BigInteger, num2: BigInteger, operator: String): String{
        when(operator){
            "*" -> { return (num1*num2).toString()}
            "+" -> { return (num1+num2).toString()}
            "-" -> { return (num1-num2).toString()}
            "/" -> { return (num1/num2).toString()}
        }
        return ""
    }

    suspend fun saveToDB(expression: String, resultText: String){
        insertHistoryUseCase(HistoryEntity(null,expression,resultText))
    }

    suspend fun getAllHistory(): List<HistoryEntity>{
        return getAllHistoryUseCase()
    }

    suspend fun deleteAllHistory(){
        deleteAllHistoryUseCase()
    }
}