package com.berkedursunoglu.a.shooping

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.berkedursunoglu.a.model.ShoopingModel
import com.berkedursunoglu.a.services.ShoopingDatabase
import kotlinx.coroutines.*

class ShoopingActivityViewModel: ViewModel() {

    val shopList = MutableLiveData<List<ShoopingModel>>()


    fun addShoopingItem(context: Context,string:String){
        viewModelScope.launch(Dispatchers.IO) {
            var shopItem = ShoopingModel(string)
            ShoopingDatabase.invoke(context).shopDao().insertAllshooping(shopItem)
        }
    }

    fun getAllDataFromRoomDatabase(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            var getData = ShoopingDatabase.invoke(context).shopDao().getAllShooping()
            withContext(Dispatchers.Main){
                shopList.value = getData
            }
        }
    }

    fun deleteAll(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            ShoopingDatabase.invoke(context).shopDao().deleteShooping()
        }
    }

}



