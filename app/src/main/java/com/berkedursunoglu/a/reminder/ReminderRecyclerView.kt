package com.berkedursunoglu.a.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.model.ReminderModel

class ReminderRecyclerView(val arrayListReminder:List<ReminderModel>):RecyclerView.Adapter<RecyclerViewReminderViewHolder>() {
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
    }

    override fun getItemCount(): Int {
        return arrayListReminder.size
    }
}

class RecyclerViewReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemDesc = itemView.findViewById<TextView>(R.id.reminder_decs_textview)
    val itemDate = itemView.findViewById<TextView>(R.id.date_desc_textview)
    val itemTime = itemView.findViewById<TextView>(R.id.time_decs_textview)

}
