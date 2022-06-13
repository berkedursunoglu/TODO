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

class EditFragmentViewModel:ViewModel() {

    val editData = MutableLiveData<String>()


    fun insertData(context: Context,item:NoteModel){
        viewModelScope.launch(Dispatchers.IO) {
            NoteDatabase.invoke(context).noteDao().insertAllNote(item)
        }
    }

    fun getDatawithUUID(context: Context ,uuid:Int){
        viewModelScope.launch(Dispatchers.IO) {
            var getData = NoteDatabase.invoke(context).noteDao().getwithUUID(uuid)
            withContext(Dispatchers.Main){
                editData.value = getData.note
                println(getData.note)
            }
        }
    }

    fun editData(context: Context,uuid:Int,text:String){
        viewModelScope.launch(Dispatchers.IO) {
            NoteDatabase.invoke(context).noteDao().editNote(uuid,text)
        }
    }

}