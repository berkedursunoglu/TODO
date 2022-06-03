package com.berkedursunoglu.a.reminder

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.util.Log

class AlarmReceiver:BroadcastReceiver() {
    private val TAG = "AlarmReceiver"
    override fun onReceive(p0: Context?, p1: Intent?) {

        Log.v(TAG, "onReceive: çalıştı")
        mp(p0!!)

        if (p1?.action == "android.intent.action.BOOT_COMPLETED") {
            Log.d(TAG, "onReceive: çalıştı")
        }

    }

    private fun mp (context:Context){
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val mp = MediaPlayer.create(context,uri)
        mp.start()
    }


}