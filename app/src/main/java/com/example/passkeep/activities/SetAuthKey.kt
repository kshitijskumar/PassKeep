package com.example.passkeep.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.example.passkeep.R
import com.example.passkeep.database.AuthenticationKey
import com.example.passkeep.database.PassKeepDatabase
import kotlinx.android.synthetic.main.activity_set_auth_key.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetAuthKey : AppCompatActivity() {
    private val TAG: String="SetAuthKey"

    private val database by lazy { Room.databaseBuilder(this, PassKeepDatabase::class.java,"passkeepdb").build() }
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"In onCreate: Called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_auth_key)

        val oldPw: String = intent.getStringExtra("oldPw").toString()
        Log.d(TAG,"In SetAuthKey: oldpw is $oldPw")

        if (oldPw=="PASS") {
            oldPwView.visibility = View.GONE
            setBtn.setOnClickListener(View.OnClickListener {
                Log.d(TAG,"In setBtn: Entered setonclicklistener with pw $oldPw")
                if((newPwView.text.isBlank()) or (confirmPwView.text.isBlank())){
                    Toast.makeText(this,"Fields can't be empty",Toast.LENGTH_SHORT).show()
                }else{
                    Log.d(TAG,"In setBtn: old pw is $oldPw")
                    if (newPwView.text.toString()==confirmPwView.text.toString()){

                        CoroutineScope(IO).launch {
                            Log.d(TAG,"when new==confirm: coroutine ${Thread.currentThread().name}")
                            updateInDb(newPwView.text.toString())
                            Log.d(TAG,"when new==confirm: pw set scope ${Thread.currentThread().name}")
                        }
                    }else{
                        Toast.makeText(this,"Authentication Key Mismatched",Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }else{
            setBtn.setOnClickListener(View.OnClickListener {
                Log.d(TAG,"In setBtn: Entered setonclicklistener with pw $oldPw")
                if((newPwView.text.isBlank()) or (confirmPwView.text.isBlank()) or oldPwView.text.isBlank()){
                    Toast.makeText(this,"Fields can's be empty",Toast.LENGTH_SHORT).show()
                }else{
                    if (oldPwView.text.toString()==oldPw){
                        Log.d(TAG,"checking old pw for update: Matched")
                        if (newPwView.text.toString()==confirmPwView.text.toString()){

                            CoroutineScope(IO).launch {
                                Log.d(TAG,"when new==confirm: coroutine ${Thread.currentThread().name}")
                                updateInDb(newPwView.text.toString())
                                Log.d(TAG,"when new==confirm: pw set scope ${Thread.currentThread().name}")
                            }
                        }else{
                            Toast.makeText(this,"Authentication Key Mismatched",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this,"Enter correct Authentication Key",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }

    }



    private suspend fun updateInDb(newPwView: String){
        Log.d(TAG,"In updateInDb: Entered in scope ${Thread.currentThread().name}")
        database.authKeyDao.updateAuthKey(AuthenticationKey(0,newPwView))
        Log.d(TAG,"In updateInDb: update query called")
        withContext(Main){
            Log.d(TAG,"In updateInDb: Scope ${Thread.currentThread().name}")
            Toast.makeText(this@SetAuthKey,"Authentication Key Set",Toast.LENGTH_SHORT).show()
            val intent= Intent(this@SetAuthKey, MainActivity::class.java)
            startActivity(intent)
            Log.d(TAG,"In updateInDb: At end of main scope")
        }
        Log.d(TAG,"In updateInDb: At end of function")
    }

}