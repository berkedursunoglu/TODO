package com.berkedursunoglu.a.services

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.berkedursunoglu.a.model.NoteModel
import com.berkedursunoglu.a.model.ShoopingModel

@Dao
interface Dao {

    @Insert
    suspend fun insertAllshooping(vararg shop: ShoopingModel)

    @Query("DELETE FROM shop")
    suspend fun deleteShooping()

    @Query("SELECT * FROM shop")
    suspend fun getAllShooping(): List<ShoopingModel>




}