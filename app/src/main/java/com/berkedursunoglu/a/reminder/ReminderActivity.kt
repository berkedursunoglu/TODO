package com.berkedursunoglu.a.reminder

import android.app.*
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.ActivityReminderBinding
import com.berkedursunoglu.a.databinding.ReminderAlertdialogBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class ReminderActivity : AppCompatActivity() {

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private lateinit var dataBinding: ActivityReminderBinding
    private lateinit var rv: ReminderRecyclerView
    private lateinit var viewModel: ReminderViewModel
    private lateinit var dataBindingReminder: ReminderAlertdialogBinding
    private val cal = Calendar.getInstance()
    var setLongClock:Long  = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_reminder)
        dataBindingReminder = DataBindingUtil.inflate(this.layoutInflater, R.layout.reminder_alertdialog, null, false)
        viewModel = ViewModelProvider(this)[ReminderViewModel::class.java]

        dataBinding.addReminder.setOnClickListener {
            alertDialog()
            unsetAlarm()

        }
    }

    private fun alertDialog() {
        dataBindingReminder = DataBindingUtil.inflate(this.layoutInflater, R.layout.reminder_alertdialog, null, false)
        alertDialogsetTime()
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(dataBindingReminder.root)
        alertDialog.setTitle("Hatırlatma")
        alertDialog.setMessage("Gerekli bilgileri doldurunuz")
        alertDialog.setPositiveButton("Tamam") { _, _ ->
            if (checkTime(cal.timeInMillis)){
                setAlarm()
                Toast.makeText(this.applicationContext,"Hatırlatma Kuruldu",Toast.LENGTH_LONG).show()
            }
        }
        alertDialog.setNegativeButton("İptal") { _, _ ->
            unsetAlarm()
        }

        dataBindingReminder.calenderButton.setOnClickListener {
            datePicker(it.context)
        }

        dataBindingReminder.clockButton.setOnClickListener {
            timerPicker(it.context)
        }

        alertDialog.show()
    }

    private fun checkTime(setMillis: Long):Boolean {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis
        val abs = abs(currentTime-setMillis)
        if (abs < 10000){
            Toast.makeText(this.applicationContext,"Min. 5 dakika sonrasına hatırlatma kurabilirsiniz.",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun setAlarm() {
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //var intrequest = sharedInteger()

        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            intent.putExtra("key","start")
            val flags = FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            PendingIntent.getBroadcast(this, 1, intent, flags)
        }

        //createNotificationChannel()

        var setAlarmMillis = cal.timeInMillis


        alarmMgr?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,setAlarmMillis,alarmIntent)

    }

    private fun datePicker(context: Context) {
        val datePicker = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
            cal.set(Calendar.YEAR,i)
            cal.set(Calendar.MONTH,i2)
            cal.set(Calendar.DAY_OF_MONTH,i3)
            var format = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
            dataBindingReminder.reminderDatetextview.text = "Tarih: $format"
        }
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context,datePicker,year,month,day).show()
    }

    private fun timerPicker(context: Context) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            var format = SimpleDateFormat("HH:mm").format(cal.time)
            dataBindingReminder.reminderTimetextview.text = "Saat: $format"
        }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    private fun alertDialogsetTime(){
        cal.get(Calendar.YEAR)
        cal.get(Calendar.MONTH)
        cal.get(Calendar.DAY_OF_MONTH)
        var formatDate = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
        dataBindingReminder.reminderDatetextview.text = "Tarih: $formatDate"
        cal.get(Calendar.HOUR_OF_DAY)
        cal.get(Calendar.MINUTE)
        var formatClock = SimpleDateFormat("HH:mm").format(cal.time)
        dataBindingReminder.reminderTimetextview.text = "Saat: $formatClock"
    }

    private fun sharedInteger():Int{
        val shared = this.getSharedPreferences("uuidInt", MODE_PRIVATE)
        var edit = shared.edit()
        val int = shared.getInt("int",0)
        var intplus = int+1
        edit.putInt("int",intplus).commit()
        return intplus
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "remindernotification"
            val descriptionText = "alarmnotification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            }
        }

    private fun unsetAlarm(){
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent = Intent(this, AlarmReceiver::class.java).let {
            it.putExtra("key","stop")
            val flags = FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            PendingIntent.getBroadcast(this, 1, it, flags)
        }
        alarmMgr?.cancel(myIntent)
    }

}