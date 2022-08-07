package com.develop.chlqudco.mvvmcalculator.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.develop.chlqudco.mvvmcalculator.data.entity.HistoryEntity
import com.develop.chlqudco.mvvmcalculator.databinding.HistoryRowBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHoler>() {

    var historyList: List<HistoryEntity> = emptyList()

    inner class ViewHoler(val binding: HistoryRowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(history: HistoryEntity){
            binding.expressionTextView.text = history.expressionText
            binding.resultTextView.text = history.resultText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        return ViewHoler(HistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}