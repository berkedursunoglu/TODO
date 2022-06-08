package com.berkedursunoglu.a.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.model.ReminderModel
import com.berkedursunoglu.a.services.ReminderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderRecyclerView(var arrayListReminder:ArrayList<ReminderModel>):RecyclerView.Adapter<RecyclerViewReminderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_cardview,parent,false)
        return RecyclerViewReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewReminderViewHolder, position: Int) {
        holder.itemDesc.text = arrayListReminder[position].descReminder
        holder.itemDate.text = arrayListReminder[position].dateReminder
        holder.itemTime.text = arrayListReminder[position].clockReminder
        holder.itemView.setOnClickListener {
            var requestCode = arrayListReminder[position].requestCode
            var position = position
            alertDiaglog(holder.itemView.context,requestCode,position)
        }
    }

    override fun getItemCount(): Int {
        return arrayListReminder.size
    }

    private fun alertDiaglog(context: Context,requestCode:Int,position: Int){
        val alert = AlertDialog.Builder(context)
        alert.setTitle("Hatırlatıcı")
        alert.setMessage("Hatırlatıcı iptal edilsin mi?")
        alert.setPositiveButton("Evet"){ diaglog,which ->

            var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context.applicationContext,ReminderService::class.java).let {
                PendingIntent.getService(context.applicationContext,requestCode,it,PendingIntent.FLAG_IMMUTABLE)
            }
            alarmManager.cancel(intent)

            GlobalScope.launch(Dispatchers.IO) {
                ReminderDatabase.invoke(context).reminderDao().deleteReminder(arrayListReminder[position].requestCode)

            }

            notifyDataSetChanged()


        }
        alert.setNegativeButton("Hayır"){diaglog,which ->

        }
        alert.show()
    }

}

class RecyclerViewReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemDesc = itemView.findViewById<TextView>(R.id.reminder_decs_textview)
    val itemDate = itemView.findViewById<TextView>(R.id.date_desc_textview)
    val itemTime = itemView.findViewById<TextView>(R.id.time_decs_textview)

}
