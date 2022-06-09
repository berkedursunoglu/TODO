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
import kotlin.math.log

class ReminderRecyclerView(var arrayListReminder: ArrayList<ReminderModel>) :
    RecyclerView.Adapter<RecyclerViewReminderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewReminderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.reminder_cardview, parent, false)
        return RecyclerViewReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewReminderViewHolder, position: Int) {
        holder.itemDesc.text = arrayListReminder[position].descReminder
        holder.itemDate.text = arrayListReminder[position].dateReminder
        holder.itemTime.text = arrayListReminder[position].clockReminder
        holder.itemView.setOnClickListener {
            var requestCode = arrayListReminder[position].requestCode
            Log.e("TAG", "onBindViewHolder: $requestCode")
            var position = position
            alertDiaglog(holder.itemView.context, requestCode, position)
        }
    }

    override fun getItemCount(): Int {
        return arrayListReminder.size
    }

    private fun alertDiaglog(context: Context, requestCode: Int, position: Int) {
        Log.e("TAG", "alertDiaglog: $position", )
        Log.e("TAG", "alertDiaglog:array size ${arrayListReminder.size}", )
        val alert = AlertDialog.Builder(context)
        alert.setTitle("Hatırlatıcı")
        alert.setMessage("Hatırlatıcı iptal edilsin mi?")
        alert.setPositiveButton("Evet") { diaglog, which ->
            var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context.applicationContext, AlarmReceiver::class.java)
            var flag = PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
            var pendingIntent =
                PendingIntent.getBroadcast(context.applicationContext, requestCode, intent, flag)
            alarmManager.cancel(pendingIntent)
            GlobalScope.launch(Dispatchers.IO) {
                ReminderDatabase.invoke(context).reminderDao()
                    .deleteReminder(arrayListReminder[position].requestCode)
                withContext(Dispatchers.Main){
                    notifyItemRemoved(position)
                    arrayListReminder.removeAt(position)
                }

            }

        }
        alert.setNegativeButton("Hayır") { diaglog, which ->

        }
        alert.show()
    }
}

class RecyclerViewReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemDesc = itemView.findViewById<TextView>(R.id.reminder_decs_textview)
    val itemDate = itemView.findViewById<TextView>(R.id.date_desc_textview)
    val itemTime = itemView.findViewById<TextView>(R.id.time_decs_textview)

}
