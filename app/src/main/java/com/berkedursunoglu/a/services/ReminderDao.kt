package com.berkedursunoglu.a.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.berkedursunoglu.a.model.ReminderModel
import java.util.ArrayList

@Dao
interface ReminderDao {

    @Insert
    suspend fun insertReminder(vararg reminder:ReminderModel)

    @Query("DELETE FROM reminder WHERE requestCode = :requestCode")
    suspend fun deleteReminder(requestCode: Int)

    @Query("SELECT * FROM reminder")
    suspend fun getAllReminder(): List<ReminderModel>
}