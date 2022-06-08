package com.berkedursunoglu.a.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
            val desc = p1?.getStringExtra("desc")
            var intent = Intent(p0?.applicationContext,ReminderService::class.java)
            intent.putExtra("desc",desc)
            p0?.applicationContext?.startService(intent)
        Log.e("TAG", "onReceive: broadcast çalıştı", )
    }

}