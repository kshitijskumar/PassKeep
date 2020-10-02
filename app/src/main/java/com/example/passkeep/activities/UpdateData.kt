package com.example.passkeep.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.example.passkeep.R
import com.example.passkeep.database.PassKeepDatabase
import kotlinx.android.synthetic.main.activity_update_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@Suppress("RedundantSamConstructor")
class UpdateData : AppCompatActivity() {

    private val database by lazy { Room.databaseBuilder(this, PassKeepDatabase::class.java,"passkeepdb").build() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        updateBtn.setOnClickListener(View.OnClickListener {
            if((siteView.text.isEmpty()) or (usernameView.text.isEmpty()) or(pwView.text.isEmpty())){
                Toast.makeText(this,"Fields can't be left Empty",Toast.LENGTH_SHORT).show()
            }else{
                CoroutineScope(IO).launch {
                    val status= checkForData(siteView.text.toString().toUpperCase(Locale.ROOT),usernameView.text.toString().replace(" ","").toLowerCase(
                        Locale.ROOT))
                    if (status){
                        database.loginDao.loginUpdate(siteView.text.toString().toUpperCase(Locale.ROOT),
                            usernameView.text.toString().replace(" ","").toLowerCase(Locale.ROOT),
                        pwView.text.toString())

                        withContext(Main){
                            Toast.makeText(this@UpdateData,"Record updated", Toast.LENGTH_SHORT).show()
                            val intent= Intent(this@UpdateData, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }else{
                        withContext(Main){
                            Toast.makeText(this@UpdateData,"No matching data found", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        })
    }

    private suspend fun checkForData(site: String,username: String): Boolean{
        val dbList= database.loginDao.getListOfWebsites()
        var flag=0
        for (i in dbList){
            if((i.site == site) and (i.userid==username)){
                flag=1
                break
            }
        }
        return flag==1
    }

}