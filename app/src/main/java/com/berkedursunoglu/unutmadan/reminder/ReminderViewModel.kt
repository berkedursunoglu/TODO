package com.berkedursunoglu.unutmadan.reminder

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkedursunoglu.unutmadan.model.ReminderModel
import com.berkedursunoglu.unutmadan.services.ReminderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList


class ReminderViewModel : ViewModel() {

    var reminderArray = MutableLiveData<ArrayList<ReminderModel>>()


    fun insertDatabase(context: Context,desc:String,clock:String,date:String,timeMillis:Long,request:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val reminderModel = ReminderModel(desc,date,clock,timeMillis,request)
            ReminderDatabase.invoke(context).reminderDao().insertReminder(reminderModel)
        }
    }

    fun getAllDatabase(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            var arrayList = ReminderDatabase.invoke(context).reminderDao().getAllReminder()
            withContext(Dispatchers.Main){
                reminderArray.value = arrayList as ArrayList<ReminderModel>
            }
        }
    }

    fun deleteDatabase(context: Context,requestCode: Int){
        viewModelScope.launch(Dispatchers.IO) {
            ReminderDatabase.invoke(context).reminderDao().deleteReminder(requestCode)
        }
    }





}