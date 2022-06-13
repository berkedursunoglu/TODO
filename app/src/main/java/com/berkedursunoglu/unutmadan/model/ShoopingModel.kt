package com.berkedursunoglu.unutmadan.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class ShoopingModel(
    @ColumnInfo(name = "shopItem")
    var shoopingItem: String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}