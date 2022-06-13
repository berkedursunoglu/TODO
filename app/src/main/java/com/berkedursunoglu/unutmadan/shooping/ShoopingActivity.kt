package com.berkedursunoglu.unutmadan.shooping

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkedursunoglu.a.R
import com.berkedursunoglu.a.databinding.ActivityShoopingBinding

class ShoopingActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityShoopingBinding
    private lateinit var viewModel: ShoopingActivityViewModel
    private lateinit var rv: ShoopingRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_shooping)
        viewModel = ViewModelProvider(this)[ShoopingActivityViewModel::class.java]
        dataBinding.shoopingRecyclerView.layoutManager = LinearLayoutManager(this)
        setSupportActionBar(dataBinding.shopToolbar)


        dataFromRoom()
        dataBinding.shoopingAddButton.setOnClickListener {
            addShopItem()
        }


    }

    private fun addShopItem() {
        val shopItemEditText = dataBinding.shoopingAddEdittext.text.toString()
        if (shopItemEditText != "") {
            viewModel.addShoopingItem(this, shopItemEditText)
            dataBinding.shoopingAddEdittext.text.clear()
            dataFromRoom()
        }

    }

    private fun dataFromRoom() {
        viewModel.getAllDataFromRoomDatabase(this)
        viewModel.shopList.observe(this) {
            rv = ShoopingRecyclerView(it)
            dataBinding.shoopingRecyclerView.adapter = rv
        }
    }

    private fun deleteAll() {
        viewModel.deleteAll(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.shooping_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_trash -> {
                var alart = AlertDialog.Builder(this@ShoopingActivity)
                alart.setTitle("Alışveriş Listesi")
                alart.setMessage("Tüm liste silinecektir bunu yapmak istiyor musunuz?")
                alart.setPositiveButton("Evet"){a,b->
                    deleteAll()
                    dataFromRoom()
                }
                alart.setNegativeButton("Hayır"){a,b->

                }
                alart.show()
                true
            }
            else -> return false
        }
    }




}