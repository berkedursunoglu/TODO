package com.berkedursunoglu.a.reminder

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.ReminderAlertdialogBinding
import com.berkedursunoglu.a.model.ReminderModel
import com.berkedursunoglu.a.services.ReminderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
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