package com.berkedursunoglu.unutmadan.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.berkedursunoglu.unutmadan.model.ShoopingModel

@Dao
interface Dao {

    @Insert
    suspend fun insertAllshooping(vararg shop: ShoopingModel)

    @Query("DELETE FROM shop")
    suspend fun deleteShooping()

    @Query("SELECT * FROM shop")
    suspend fun getAllShooping(): List<ShoopingModel>




}