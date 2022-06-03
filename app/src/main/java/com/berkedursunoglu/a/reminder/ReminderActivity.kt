package com.berkedursunoglu.a.reminder

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.ActivityReminderBinding
import com.berkedursunoglu.a.databinding.ReminderAlertdialogBinding
import com.berkedursunoglu.a.model.ReminderModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class ReminderActivity : AppCompatActivity() {

    private lateinit var dataBindingReminder: ReminderAlertdialogBinding
    private lateinit var dataBinding: ActivityReminderBinding
    private lateinit var viewModel: ReminderViewModel
    private lateinit var rv: ReminderRecyclerView
    private val cal = Calendar.getInstance()
    private lateinit var alarmManager: AlarmManager

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_reminder)
        dataBindingReminder = DataBindingUtil.inflate(this.layoutInflater, R.layout.reminder_alertdialog, null, false)
        dataBinding.reminderRecyclerview.layoutManager = LinearLayoutManager(this.applicationContext)
        viewModel = ViewModelProvider(this)[ReminderViewModel::class.java]
        recyclerView()
        dataBinding.addReminder.setOnClickListener {
            alertDialog()

        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun alertDialog() {
        dataBindingReminder = DataBindingUtil.inflate(this.layoutInflater, R.layout.reminder_alertdialog, null, false)
        alertDialogsetTime()
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(dataBindingReminder.root)
        alertDialog.setTitle("Hatırlatma")
        alertDialog.setMessage("Gerekli bilgileri doldurunuz")
        alertDialog.setPositiveButton("Tamam") { _, _ ->
            if (checkTime(cal.timeInMillis)){
                alarmManager()
                Toast.makeText(this.applicationContext,"Hatırlatma Kuruldu",Toast.LENGTH_LONG).show()
            }
        }
        alertDialog.setNegativeButton("İptal") { _, _ ->
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


    @RequiresApi(Build.VERSION_CODES.S)
    private fun alarmManager(){
        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.canScheduleExactAlarms()
        var requestCode = sharedInteger()
        val intent = Intent(this.applicationContext,AlarmReceiver::class.java).let {
            val flag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
            PendingIntent.getBroadcast(this.applicationContext,requestCode,it,flag)
        }

        val timeMillis = cal.timeInMillis

        val receiver = ComponentName(this.applicationContext, AlarmReceiver::class.java)

        this.packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeMillis,intent)
        val datetext = dataBindingReminder.reminderDatetextview.text.toString()
        val clocktext = dataBindingReminder.reminderTimetextview.text.toString()
        var desc = dataBindingReminder.reminderEdittext.text.toString()
        if(desc == ""){
            desc = "Hatırlatma"
        }
        viewModel.insertDatabase(this.applicationContext,desc,clocktext,datetext,timeMillis,requestCode)
        recyclerView()

    }

    private fun recyclerView(){
        viewModel.getAllDatabase(this.applicationContext)
        viewModel.reminderArray.observe(this){
            rv = ReminderRecyclerView(it)

            dataBinding.reminderRecyclerview.adapter = rv
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun cancelAlarm(){
        viewModel.deleteDatabase(this.applicationContext,1)
        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.canScheduleExactAlarms()
        val intent = Intent(this.applicationContext,AlarmReceiver::class.java).let {
            val flag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
            PendingIntent.getBroadcast(this.applicationContext,0,it,flag)
        }

        val timeMillis = cal.timeInMillis

        val receiver = ComponentName(this.applicationContext, AlarmReceiver::class.java)

        this.packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeMillis,intent)
    }
}