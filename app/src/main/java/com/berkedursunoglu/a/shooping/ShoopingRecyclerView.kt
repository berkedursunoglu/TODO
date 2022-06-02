package com.berkedursunoglu.a.shooping

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.model.ShoopingModel

class ShoopingRecyclerView(var list:List<ShoopingModel>): RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shooping_cardview,parent,false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.shopTextview.text = list[position].shoopingItem
        holder.shopTextview.setOnClickListener {
            if (holder.shopTextview.paintFlags == Paint.STRIKE_THRU_TEXT_FLAG){
                holder.shopTextview.paintFlags = Paint.DEV_KERN_TEXT_FLAG
            }else{
                holder.shopTextview.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val shopTextview: TextView = view.findViewById(R.id.shooping_textview)
}
