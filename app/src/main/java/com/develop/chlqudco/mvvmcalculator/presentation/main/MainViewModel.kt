package com.develop.chlqudco.mvvmcalculator.presentation.main

import android.util.Log
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

    suspend fun saveToDB(expression: String, resultText: String){
        insertHistoryUseCase(HistoryEntity(null,expression,resultText))
    }

    suspend fun getAllHistory(): List<HistoryEntity>{
        return getAllHistoryUseCase()
    }

    suspend fun deleteAllHistory(){
        deleteAllHistoryUseCase()
    }

    //후위 수식을 이용한 계산
    fun calculate(expressionText: String): String{

        //후위수식으로 바꾼 문자열
        var postfixStack: MutableList<String> = mutableListOf()
        var operatorStack: MutableList<Char> = mutableListOf()

        //문자 하나씩 꺼내와서
        var currentString: String = ""
        for (currentText in expressionText){
           //분기.. 이게 맞나?
            when(currentText){
                //숫자면은 무조건 넣기
                in '0'..'9' ->{
                    currentString += currentText.toString()
                }
                //무조건 스택에 넣기?
                '*' -> {
                    postfixStack.add(currentString)
                    currentString = ""

                    operatorStack.add(currentText)
                }
                '/' -> {
                    postfixStack.add(currentString)
                    currentString = ""

                    operatorStack.add(currentText)
                }
                '-' -> {
                    postfixStack.add(currentString)
                    currentString = ""

                    //안비어 있고 맨 위의 연산자가 * 나 / 면 빼기
                    while(operatorStack.size > 0 && (operatorStack[operatorStack.size-1]=='*' || operatorStack[operatorStack.size-1]=='/')){
                        postfixStack.add(operatorStack[operatorStack.size-1].toString())
                        operatorStack.removeLast()
                    }
                    operatorStack.add(currentText)
                }
                '+' -> {
                    postfixStack.add(currentString)
                    currentString = ""

                    //안비어 있고 맨 위의 연산자가 * 나 / 면 빼기
                    while(operatorStack.size > 0 && (operatorStack[operatorStack.size-1]=='*' || operatorStack[operatorStack.size-1]=='/')){
                        postfixStack.add(operatorStack[operatorStack.size-1].toString())
                        operatorStack.removeLast()
                    }
                    operatorStack.add(currentText)
                }
                else -> {

                }
            }

        }

        //남은거 옮기기
        postfixStack.add(currentString)

        while (operatorStack.isNotEmpty()){
            postfixStack.add(operatorStack[operatorStack.size-1].toString())
            operatorStack.removeLast()
        }

        //출력
        Log.e("asdasd",postfixStack.toString())

        //후위수식 계산
        var resultStack: MutableList<String> = mutableListOf()
        postfixStack.forEach {
            //연산자면
            if(it == "*" || it == "/" || it == "+" || it == "-" ){
                val num1 = resultStack[resultStack.size - 1].toBigInteger()
                val num2 = resultStack[resultStack.size - 2].toBigInteger()

                resultStack.removeLast()
                resultStack.removeLast()

                when(it){
                    "*" -> {
                        resultStack.add((num1*num2).toString())
                    }
                    "/" -> {
                        resultStack.add((num2/num1).toString())
                    }
                    "+" -> {
                        resultStack.add((num1+num2).toString())
                    }
                    "-" -> {
                        resultStack.add((num2-num1).toString())
                    }
                }
            }
            else{
                resultStack.add(it)
            }
        }

        Log.e("asdasd",resultStack[0])
        return resultStack[0]
    }
}