package com.berkedursunoglu.a.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.shooping.ShoopingActivity
import com.berkedursunoglu.a.databinding.ActivityMainBinding
import com.berkedursunoglu.a.notes.NoteActivity
import com.berkedursunoglu.a.reminder.ReminderActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.mainActivity = this
    }


    fun shoopingActivity(){
        startActivity(Intent(this, ShoopingActivity::class.java))
    }


    fun noteActivity(){
        startActivity(Intent(this, NoteActivity::class.java))
    }

    fun reminderActivity(){
        startActivity(Intent(this, ReminderActivity::class.java))
    }
}