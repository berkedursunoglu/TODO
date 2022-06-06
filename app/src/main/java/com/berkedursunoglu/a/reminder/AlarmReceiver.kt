package com.berkedursunoglu.a.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.berkedursunoglu.a.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {
    private val TAG = "AlarmReceiver"
    override fun onReceive(p0: Context?, p1: Intent?) {

        var desc = p1?.getStringExtra("desc")
        var intent = Intent(p0?.applicationContext,ReminderService::class.java)
        intent.putExtra("desc",desc)
        p0?.applicationContext?.startService(intent)

    }

}