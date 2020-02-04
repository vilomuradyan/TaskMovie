package com.vilo.muradyan.task.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vilo.muradyan.task.data.model.Results

@Database(entities = [Results::class], version = 7)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getMovieDao():MovieDao
}