package com.berkedursunoglu.a.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.berkedursunoglu.a.model.ShoopingModel


@Database(entities = [ShoopingModel::class], version = 1)
abstract class ShoopingDatabase : RoomDatabase() {
    abstract fun shopDao(): Dao

    companion object {

        @Volatile
        private var instance: ShoopingDatabase? = null

        private var lock = Any()

        operator fun invoke(context: Context) = instance ?: kotlin.synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ShoopingDatabase::class.java,
            "shop"
        ).build()
    }


}