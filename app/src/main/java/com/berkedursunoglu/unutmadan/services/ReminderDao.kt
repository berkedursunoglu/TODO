package com.berkedursunoglu.unutmadan.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.berkedursunoglu.unutmadan.model.ReminderModel

@Dao
interface ReminderDao {

    @Insert
    suspend fun insertReminder(vararg reminder:ReminderModel)

    @Query("DELETE FROM reminder WHERE requestCode = :requestCode")
    suspend fun deleteReminder(requestCode: Int)

    @Query("SELECT * FROM reminder")
    suspend fun getAllReminder(): List<ReminderModel>
}