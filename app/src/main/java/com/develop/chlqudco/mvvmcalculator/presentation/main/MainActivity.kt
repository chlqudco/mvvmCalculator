package com.develop.chlqudco.mvvmcalculator.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.chlqudco.mvvmcalculator.R
import com.develop.chlqudco.mvvmcalculator.databinding.ActivityMainBinding
import com.develop.chlqudco.mvvmcalculator.presentation.BaseActivity
import com.develop.chlqudco.mvvmcalculator.presentation.adapter.HistoryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel by viewModel<MainViewModel>()
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private val adapter: HistoryAdapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.mainHistoryRecyclerView.adapter = adapter
        binding.mainHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    //C 버튼 클릭
    fun clearButtonClicked(view: View) = with(binding) {
        viewModel.initOperator()
        viewModel.isDot = false
        mainExpressionTextView.text = ""
        mainResultTextView.text = ""
    }

    fun buttonClicked(v: View) {
        //id에 따라 분기
        when (v.id) {
            R.id.button0 -> numberButtonClicked("0")
            R.id.button1 -> numberButtonClicked("1")
            R.id.button2 -> numberButtonClicked("2")
            R.id.button3 -> numberButtonClicked("3")
            R.id.button4 -> numberButtonClicked("4")
            R.id.button5 -> numberButtonClicked("5")
            R.id.button6 -> numberButtonClicked("6")
            R.id.button7 -> numberButtonClicked("7")
            R.id.button8 -> numberButtonClicked("8")
            R.id.button9 -> numberButtonClicked("9")

            R.id.buttonPlus -> operatorButtonClicked("+")
            R.id.buttonMinus -> operatorButtonClicked("-")
            R.id.buttonMulti -> operatorButtonClicked("*")
            R.id.buttonDivider -> operatorButtonClicked("/")

            R.id.buttonDot -> dotButtonClicked()

            R.id.openBracketButton -> openBracketButtonClicked()
            R.id.closeBracketButton -> closeBracketButtonClicked()
        }
    }

    private fun openBracketButtonClicked() {
        val expressionText = binding.mainExpressionTextView.text.toString()

        //예외처리 1. 숫자 뒤에 바로 괄호가 오면 x 추가
        if(expressionText.isNotEmpty() && (expressionText[expressionText.length - 1] in '0'..'9')){
            binding.mainExpressionTextView.append(" * (")
            viewModel.openBracketCount += 1
            viewModel.hasOperator = true
            return
        }
        binding.mainExpressionTextView.append("(")
        viewModel.openBracketCount += 1

    }

    private fun closeBracketButtonClicked() {
        //열기 0개면 탈출
        if (viewModel.openBracketCount == 0){
            return
        } else{
            binding.mainExpressionTextView.append(")")
            viewModel.openBracketCount -= 1
        }
    }

    private fun dotButtonClicked() {
        val totalText = binding.mainExpressionTextView.text.toString()
        val expressionText = binding.mainExpressionTextView.text.split(" ")

        //예외처리 0. 이미 찍은 경우
        if (viewModel.isDot){
            return
        }

        //예외처리 1. 비어있는 경우
        if (totalText.isEmpty()){
            binding.mainExpressionTextView.text = "0."
            viewModel.isDot = true
            return
        }

        //예외처리 2. 숫자의 시작이 .인 경우
        if(viewModel.isOperator){
            binding.mainExpressionTextView.append(" 0.")
            viewModel.isDot = true
            viewModel.isOperator = false
            return
        }

        //예외처리 3. 15자리가 넘어가지 않도록
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리 넘어가지 말아오", Toast.LENGTH_SHORT).show()
            return
        }

        //다 아니면 점찍어
        binding.mainExpressionTextView.append(".")

        viewModel.isDot = true
    }

    //숫자를 눌렀을 경우
    private fun numberButtonClicked(number: String) {

        //직전에 입력한게 연산자면 한칸 띄어
        if (viewModel.isOperator) {
            binding.mainExpressionTextView.append(" ")
        }

        //일단 무적건 숫자니까 false 주기
        viewModel.isOperator = false

        //텍스트 가져와서 뒤에 넣기
        val expressionText = binding.mainExpressionTextView.text.split(" ")

        //예외처리 1. 15자리가 넘어가지 않도록
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리 넘어가지 말아오", Toast.LENGTH_SHORT).show()
            return
        }

        //예외처리 2. 맨앞에 0이 오면 추가X
        if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0으로 시작하지 말아오", Toast.LENGTH_SHORT).show()
            return
        }

        //마지막으로 뒤에 붙이기
        binding.mainExpressionTextView.append(number)
        //결과 텍스트뷰에 추가하기
        if (viewModel.openBracketCount == 0){
            binding.mainResultTextView.text = calculateExpression()
        }

    }

    //계산 결과값 반환해주는 함수
    private fun calculateExpression(): String {
        val expressionText = binding.mainExpressionTextView.text.toString()
        val splitExpressionText = expressionText.split(" ")

        //예외 처리 1.완성된 수식인지 검사
        if (viewModel.hasOperator.not() || splitExpressionText.size < 3) {
            return ""
        }

        return viewModel.calculate(expressionText)
    }

    //연산자를 눌렀을 경우
    @SuppressLint("SetTextI18n")
    private fun operatorButtonClicked(operator: String) {

        //예외처리 1.아직 아무것도 입력 안한경우 무시
        if (binding.mainExpressionTextView.text.isEmpty()) {
            return
        }

        //예외처리 2. 직전 입력이 열린 괄호면 무시
        if (binding.mainExpressionTextView.text.toString()[binding.mainExpressionTextView.text.toString().length - 1] == '('){
            return
        }

        //경우에 따라 분기
        when {
            //직전에 입력한게 연산자면 교체
            viewModel.isOperator -> {
                val text = binding.mainExpressionTextView.text.toString()
                binding.mainExpressionTextView.text = text.dropLast(1) + operator
            }
            //다 아니면 붙여
            else -> {
                binding.mainExpressionTextView.append(" $operator")
            }
        }

        //연산자 잘보이게 색깔 변경하기
        val ssb = SpannableStringBuilder(binding.mainExpressionTextView.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            binding.mainExpressionTextView.text.length - 1,
            binding.mainExpressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.mainExpressionTextView.text = ssb

        //방금 연산자 썻고 이젠 들어가니가
        viewModel.isOperator = true
        viewModel.hasOperator = true
        viewModel.isDot = false
    }

    //기록 버튼 클릭
    @SuppressLint("NotifyDataSetChanged")
    fun historyButtonClicked(view: View) {
        //뷰 초기화
        binding.historyLayout.isVisible = true

        //기록 불러오기
        CoroutineScope(Dispatchers.Main).launch {
            adapter.historyList = viewModel.getAllHistory()
            adapter.notifyDataSetChanged()
        }
    }

    //계산 함수
    fun resultButtonClicked(view: View) {
        val expressionText = binding.mainExpressionTextView.text.toString()
        val splitExpressionText = expressionText.split(" ")

        //예외처리 1. 완성되지 않은 수식
        if (splitExpressionText.size < 3) {
            Toast.makeText(this, "수식이 완성되지 않았습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        //결과 계산하기
        val resultText = viewModel.calculate(expressionText)

        //DB에 저장하기
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.saveToDB(expressionText, resultText)
        }

        //옮겨주기
        binding.mainExpressionTextView.text = resultText
        binding.mainResultTextView.text = ""

        //초기화
        viewModel.initOperator()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun historyClearButtonClicked(view: View) {
        //기록 초기화 하기
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.deleteAllHistory()
            adapter.historyList = viewModel.getAllHistory()
            adapter.notifyDataSetChanged()
        }
    }

    fun closeHistoryButtonClicked(view: View) {
        binding.historyLayout.isVisible = false
    }
}