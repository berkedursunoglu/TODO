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
        if (intent?.getStringExtra("alarm") == "close") {
            stopSelf()
        } else {
            var desc = intent?.getStringExtra("desc")
            mp.isLooping = true
            mp.start()
            notification(this, desc.orEmpty())
        }
        return START_STICKY
    }

    override fun onDestroy() {
        if (mp.isPlaying){
            mp.stop()
            mp.release()
        }
    }


    private fun notification(context: Context, desc: String) {
        var intent = Intent(context.applicationContext, ReminderService::class.java).let {
            it.putExtra("alarm", "close")
            PendingIntent.getService(context.applicationContext, 1, it, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
        }

            var builder = NotificationCompat.Builder(context, "reminderChannel")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Hatırlatıcı")
                .setContentText(desc)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.logo, "Tamam", intent)
                .build()
            var managerCompat = NotificationManagerCompat.from(context)
            managerCompat.notify(1, builder)
        }

    }