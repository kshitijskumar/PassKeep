package com.example.passkeep


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.passkeep.database.AuthenticationDao
import com.example.passkeep.database.AuthenticationKey
import com.example.passkeep.database.PassKeepDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PassKeepAuthTest{
    private lateinit var authDao: AuthenticationDao
    private lateinit var db: PassKeepDatabase

    @Before
    fun createDb(){
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        db= Room.inMemoryDatabaseBuilder(context, PassKeepDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        authDao=db.authKeyDao
    }

    @After
    @Throws(IOException::class)
    fun  closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun displayKey(): Unit = runBlocking{
        //val obj=AuthenticationKey(0,"PASS")
        val details: String? = authDao.checkKey()
        assertEquals(null,details)
    }

    @Test
    @Throws(Exception::class)
    fun insertDefaultKey()= runBlocking{
        val key= AuthenticationKey(0,"PASS")
        authDao.addDefaultKey(key)
        val returned: String? = authDao.checkKey()
        assertEquals(key.authKey,returned)
    }

    @Test
    @Throws(Exception::class)
    fun updateKey()= runBlocking {
        val oldKey= AuthenticationKey(0,"PASS")
        authDao.addDefaultKey(oldKey)
        val newKey= AuthenticationKey(0,"2212")
        authDao.updateAuthKey(newKey)
        val returned: String?= authDao.checkKey()
        assertEquals(newKey,returned)
    }
}
