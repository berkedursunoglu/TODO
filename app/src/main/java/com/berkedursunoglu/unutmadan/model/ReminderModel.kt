package com.berkedursunoglu.unutmadan.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder")
data class ReminderModel(
    @ColumnInfo(name = "descReminder")
    val descReminder:String,
    @ColumnInfo(name = "dateReminder")
    val dateReminder:String,
    @ColumnInfo(name = "clockReminder")
    val clockReminder:String,
    @ColumnInfo(name = "clockTimeMillis")
    val clockTimeMillis:Long,
    @ColumnInfo(name = "requestCode")
    @PrimaryKey
    val requestCode:Int
    ) {
}