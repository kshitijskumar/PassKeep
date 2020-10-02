package com.example.passkeep.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.example.passkeep.R
import com.example.passkeep.database.PassKeepDatabase
import kotlinx.android.synthetic.main.activity_enter_auth_key.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class EnterAuthKey : AppCompatActivity() {
    private val TAG: String="EnterAuthKey"

    private val database by lazy { Room.databaseBuilder(this, PassKeepDatabase::class.java,"passkeepdb").build() }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"In onCreate: onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_auth_key)

        val nextActivity= intent.getStringExtra("NextActivity").toString()

        CoroutineScope(IO).launch {
            val response: Boolean=initialCheck()
            if(response){
                withContext(Main){
                    val intent= Intent(this@EnterAuthKey, SetAuthKey::class.java).apply {
                        putExtra("oldPw","PASS")
                    }
                    Toast.makeText(this@EnterAuthKey,"Setup your Authentication Key",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
            }
        }
        
        enterBtn.setOnClickListener(View.OnClickListener {
            Log.d(TAG,"In onClickListener: Start")
            CoroutineScope(IO).launch {
                Log.d(TAG,"In onClickListener: Coroutine scope ${Thread.currentThread().name}")
                if(authKeyView.text.isBlank()){
                    withContext(Main){
                        Toast.makeText(this@EnterAuthKey,"Enter Authentication Key",Toast.LENGTH_SHORT).show()

                    }
                }else{
                    val userKey=authKeyView.text.toString()
                    Log.d(TAG,"In onClickListener:userkey:$userKey scope ${Thread.currentThread().name}")
                    val status=entryCheck(userKey)
                    Log.d(TAG,"In onClickListener: status= $status")
                    if(status){
                        withContext(Main){
                            Log.d(TAG,"In onClickListener: scope ${Thread.currentThread().name}")
                            authKeyView.setText("")
                            Toast.makeText(this@EnterAuthKey,"Access Granted",Toast.LENGTH_SHORT).show()
                            when(nextActivity){
                                "RetrieveMenu"->{
                                    val intent= Intent(this@EnterAuthKey, RetrieveMenu::class.java)
                                    startActivity(intent)
                                }
                                "UpdateData"->{
                                    val intent= Intent(this@EnterAuthKey, UpdateData::class.java)
                                    startActivity(intent)
                                }
                                "DeleteData"->{
                                    val intent= Intent(this@EnterAuthKey, DeleteData::class.java)
                                    startActivity(intent)
                                }
                            }
                        }
                    }else{
                        withContext(Main){
                            Toast.makeText(this@EnterAuthKey,"Access Denied",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            val intent= Intent(this,MainActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            }
//            startActivity(intent)
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    private suspend fun entryCheck(userKey: String): Boolean{
        Log.d(TAG,"In entryCheck: userkey recieved= $userKey")
        val actualKey=database.authKeyDao.checkKey()
        Log.d(TAG,"In entryCheck: actualkey is $actualKey")
        return actualKey==userKey
    }

    private suspend fun initialCheck():Boolean{
        val defaultPw= database.authKeyDao.checkKey()
        return defaultPw=="PASS"
    }


}