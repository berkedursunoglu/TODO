package com.berkedursunoglu.a.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteModel(
    @ColumnInfo(name = "noteItem")
    var note:String) {

    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}