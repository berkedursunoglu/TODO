package com.berkedursunoglu.unutmadan.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.berkedursunoglu.unutmadan.model.ReminderModel

@Database(entities = [ReminderModel::class], version = 1)
abstract class ReminderDatabase:RoomDatabase() {

        abstract fun reminderDao(): ReminderDao

        companion object{

            private var instance:ReminderDatabase? = null

            private var lock = Any()

            operator fun invoke(context: Context) = ReminderDatabase.instance ?: kotlin.synchronized(
                ReminderDatabase.lock
            ) {
                ReminderDatabase.instance ?: makeDatabase(context).also {
                    ReminderDatabase.instance = it
                }
            }

            private fun makeDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                ReminderDatabase::class.java,
                "reminder"
            ).build()
    }
}