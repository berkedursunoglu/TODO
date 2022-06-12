package com.berkedursunoglu.a.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.model.NoteModel
import com.berkedursunoglu.a.services.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NoteRecyclerView(var arrayList: ArrayList<NoteModel>) : RecyclerView.Adapter<RecyclerViewHold>() {

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
        holder.noteTextView.setOnLongClickListener {
            val alert = AlertDialog.Builder(it.context)
            alert.setTitle("Notlar")
            alert.setMessage("Notu silmek istiyor musun?")
            alert.setPositiveButton("Evet"){a,b->
                GlobalScope.launch(Dispatchers.IO) {
                    NoteDatabase.invoke(it.context).noteDao().deleteNoteitem(arrayList[position].uuid)
                    withContext(Dispatchers.Main){
                        notifyDataSetChanged()
                        arrayList.removeAt(position)
                    }
                }
            }
            alert.setNegativeButton("Hayır"){a,b->

            }
            alert.create().show()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

class RecyclerViewHold(view: View): RecyclerView.ViewHolder(view) {
    val noteTextView = view.findViewById<TextView>(R.id.noteTextView)
}
