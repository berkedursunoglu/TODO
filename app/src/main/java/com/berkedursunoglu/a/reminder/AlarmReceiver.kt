package com.berkedursunoglu.a.reminder

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.berkedursunoglu.a.R

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var mp: MediaPlayer

    override fun onReceive(p0: Context?, p1: Intent?) {
        var string = p1!!.getStringExtra("key")
        println(string)
        if (string == "start"){
            ringToneStart(p0!!)
        }else{
            mp.stop()
        }

    }

    private fun notification(context: Context) {
        var intent = Intent(context,ReminderActivity::class.java)
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        var pendingIntent = PendingIntent.getActivity(context.applicationContext,1,intent,flags)
        var builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.reminder)
            .setContentTitle("Hatırlatma")
            .setContentText("Hatırlatma 1")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.reminder,"Tamam",pendingIntent)
            with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }

    private fun ringToneStart(context: Context) {
        var alert: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            if (alert == null) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            }
        }
        val mp = MediaPlayer.create(context, alert)
        mp.start()
    }


}