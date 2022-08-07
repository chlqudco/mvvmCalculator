package com.develop.chlqudco.mvvmcalculator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.develop.chlqudco.mvvmcalculator.data.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    suspend fun getAllHistory(): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAllHistory()

    @Insert
    suspend fun insertHistory(historyEntity: HistoryEntity)
}