package com.berkedursunoglu.unutmadan.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.berkedursunoglu.unutmadan.model.NoteModel
@Dao
interface NoteDao {

    @Insert
    suspend fun insertAllNote(vararg note: NoteModel) : List<Long>

    @Query("DELETE FROM note")
    suspend fun deleteNote()

    @Query("SELECT * FROM note")
    suspend fun getAllNote(): List<NoteModel>

    @Query("SELECT * FROM note WHERE uuid = :uuid")
    suspend fun getwithUUID(uuid: Int): NoteModel

    @Query("UPDATE note SET noteItem = :string WHERE uuid = :uuid")
    suspend fun editNote(uuid:Int,string:String)

    @Query("DELETE FROM note WHERE uuid = :uuid")
    suspend fun deleteNoteitem(uuid: Int)


}