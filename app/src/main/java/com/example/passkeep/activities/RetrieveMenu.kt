package com.example.passkeep.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.room.Room
import com.example.passkeep.adapter.MenuItems
import com.example.passkeep.R
import com.example.passkeep.adapter.RecyclerAdapter
import com.example.passkeep.database.PassKeepDatabase
import kotlinx.android.synthetic.main.activity_retrieve_menu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetrieveMenu : AppCompatActivity() {

    private val TAG: String = "RetrieveMenu"
    private val database by lazy {
        Room.databaseBuilder(this,
            PassKeepDatabase::class.java,
            "passkeepdb").build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve_menu)

        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter=menuAdapter

        CoroutineScope(Dispatchers.IO).launch {

            val dataList = database.loginDao.getListOfWebsites()
            val lista = ArrayList<MenuItems>()
            for (i in dataList) {
                lista.add(MenuItems(i.site, i.userid, i.userpw))
            }
            withContext(Main) {
                recyclerView.adapter = RecyclerAdapter(lista)
            }
        }

        addBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddLoginData::class.java)
            startActivity(intent)
        })

    }

}