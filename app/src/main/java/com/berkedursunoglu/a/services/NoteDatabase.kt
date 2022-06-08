package com.berkedursunoglu.a.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.berkedursunoglu.a.model.NoteModel

@Database(entities = [NoteModel::class], version = 2)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun noteDao(): NoteDao


    companion object{

        private var instance:NoteDatabase? = null

        private var lock = Any()

        operator fun invoke(context: Context) = NoteDatabase.instance ?: kotlin.synchronized(
            NoteDatabase.lock
        ) {
            NoteDatabase.instance ?: makeDatabase(context).also {
                NoteDatabase.instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note"
        ).build()

    }
}