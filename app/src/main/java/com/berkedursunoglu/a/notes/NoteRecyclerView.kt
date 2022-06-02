package com.berkedursunoglu.a.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.model.NoteModel


class NoteRecyclerView(var arrayList: List<NoteModel>) : RecyclerView.Adapter<RecyclerViewHold>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHold {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_cardview,parent,false)
        return RecyclerViewHold(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHold, position: Int) {
       holder.noteTextView.text = arrayList[position].note
        holder.noteTextView.setOnClickListener {
            val action = NoteFragmentDirections.noteFragtoEditFrag(howToCame = "edit", uuid = arrayList[position].uuid)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

class RecyclerViewHold(view: View): RecyclerView.ViewHolder(view) {
    val noteTextView = view.findViewById<TextView>(R.id.noteTextView)
}
