package com.berkedursunoglu.a.reminder

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.berkedursunoglu.a.R
import java.net.URI

class ReminderService : Service() {
    private val TAG = "ReminderService"
    private lateinit var mp:MediaPlayer
    private lateinit var uri:Uri


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }



    override fun onCreate() {
        super.onCreate()
        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        mp = MediaPlayer.create(this, uri)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ON")

        if (intent?.getStringExtra("notification") == "alarmclose") {
            stopSelf()
        } else {
            var desc = intent?.getStringExtra("desc")
            mp.isLooping = true
            mp.start()
            notification(this, desc.orEmpty())
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        if (mp.isPlaying){
            mp.stop()
            mp.release()
        }
    }


    private fun notification(context: Context, desc: String) {
        var intent = Intent(context.applicationContext, ReminderService::class.java).let {
            it.putExtra("notification", "alarmclose")
            var flag = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            PendingIntent.getService(context.applicationContext, 1, it, flag)
        }

            var builder = NotificationCompat.Builder(context, "reminderChannel")
                .setSmallIcon(R.drawable.reminder)
                .setContentTitle("Hatırlatıcı")
                .setContentText(desc)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.reminder, "Tamam", intent)
                .build()
            var managerCompat = NotificationManagerCompat.from(context)
            managerCompat.notify(1, builder)
        }

    }