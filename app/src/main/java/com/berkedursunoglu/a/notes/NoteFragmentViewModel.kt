package com.berkedursunoglu.a.notes

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkedursunoglu.a.model.NoteModel
import com.berkedursunoglu.a.services.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteFragmentViewModel:ViewModel() {

    val arrayListNote = MutableLiveData<List<NoteModel>>()
    val arrayListUUID = MutableLiveData<List<Long>>()


    fun getDataFromRoom(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val arrayListDao = NoteDatabase.invoke(context).noteDao().getAllNote()
            withContext(Dispatchers.Main){
                arrayListNote.value = arrayListDao
            }
        }
    }

    fun insertDataRoom(context: Context,note:NoteModel){
        viewModelScope.launch(Dispatchers.IO) {
            val arrayListDaoUUID = NoteDatabase.invoke(context).noteDao().insertAllNote(note)
            withContext(Dispatchers.Main){
                arrayListUUID.value = arrayListDaoUUID
            }
        }
    }

    fun deleteAll(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            NoteDatabase.invoke(context).noteDao().deleteNote()
        }
    }



}