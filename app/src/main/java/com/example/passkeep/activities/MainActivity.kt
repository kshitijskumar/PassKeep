package com.example.passkeep.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import com.example.passkeep.R
import com.example.passkeep.database.AuthenticationKey
import com.example.passkeep.database.PassKeepDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("RedundantSamConstructor")
class MainActivity : AppCompatActivity() {

    private val TAG: String= "Main Activity"
    private val database by lazy { Room.databaseBuilder(this, PassKeepDatabase::class.java,"passkeepdb").build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"Main Activity: onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG,"Main Activity: In coroutine with scope ${Thread.currentThread().name}")
            setDefaultAuthKey()
        }

        menuBtn.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"In retrieveBtn: OnClickListener start")
            val intent= Intent(this, EnterAuthKey::class.java).apply {
                putExtra("NextActivity","RetrieveMenu")
            }
            startActivity(intent)
        })

        updateBtn.setOnClickListener(View.OnClickListener {
            val intent= Intent(this, EnterAuthKey::class.java).apply {
                putExtra("NextActivity","UpdateData")
            }
            startActivity(intent)
        })

        deleteBtn.setOnClickListener(View.OnClickListener {
            val intent= Intent(this, EnterAuthKey::class.java).apply {
                putExtra("NextActivity","DeleteData")
            }
            startActivity(intent)
        })

        authKeyBtn.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "In authKeyBtn: onClickListener starts")
            CoroutineScope(Dispatchers.IO).launch {
                val keyFromDb = keyRetrieve()
                val intent = Intent(this@MainActivity, SetAuthKey::class.java).apply {
                    putExtra("oldPw", keyFromDb)
                }
                startActivity(intent)
            }
        })
    }

    private suspend fun setDefaultAuthKey(){
        Log.d(TAG,"In setDefaultAuthKey: function start at ${Thread.currentThread().name}")
        val passwordInDatabase: String?=database.authKeyDao.checkKey()
        if(passwordInDatabase==null){
            database.authKeyDao.addDefaultKey(AuthenticationKey(0,"PASS"))
        }
        Log.d(TAG,"In setDefaultAuthKey: function end with key: ${database.authKeyDao.checkKey()}")
    }

    private suspend fun keyRetrieve(): String {
        Log.d(TAG,"In keyRetrieve: Fun starts at ${Thread.currentThread().name}")
        return database.authKeyDao.checkKey()

    }
}