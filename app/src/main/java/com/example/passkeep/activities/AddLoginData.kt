package com.example.passkeep.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.example.passkeep.R
import com.example.passkeep.database.Login
import com.example.passkeep.database.PassKeepDatabase
import kotlinx.android.synthetic.main.activity_add_login_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


@Suppress("RedundantSamConstructor")
class AddLoginData : AppCompatActivity() {
    private val database by lazy { Room.databaseBuilder(this, PassKeepDatabase::class.java,"passkeepdb").build() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_login_data)

        setBtn.setOnClickListener(View.OnClickListener {

            if ((siteView.text.isEmpty()) or (usernameView.text.isEmpty()) or (pwView.text.isEmpty())){
                Toast.makeText(this,"Fields can't be empty", Toast.LENGTH_SHORT).show()
            }else{
                CoroutineScope(IO).launch {
                    database.loginDao.loginInsert(Login(siteView.text.toString().toUpperCase(Locale.ROOT),usernameView.text.toString().replace(" ","")
                        .toLowerCase(Locale.ROOT), pwView.text.toString()))
                    withContext(Main){
                        Toast.makeText(this@AddLoginData,"Record added", Toast.LENGTH_SHORT).show()
                        val intent= Intent(this@AddLoginData, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }

}