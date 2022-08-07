package com.develop.chlqudco.mvvmcalculator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val expressionText: String,
    val resultText: String
)