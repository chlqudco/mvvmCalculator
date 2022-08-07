package com.develop.chlqudco.mvvmcalculator.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.develop.chlqudco.mvvmcalculator.data.dao.HistoryDao
import com.develop.chlqudco.mvvmcalculator.data.entity.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object{
        private const val DATABASE_NAME = "history.db"

        fun build(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}