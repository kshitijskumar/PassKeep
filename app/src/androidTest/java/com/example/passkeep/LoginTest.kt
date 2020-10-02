package com.example.passkeep

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.passkeep.database.Login
import com.example.passkeep.database.LoginDao
import com.example.passkeep.database.PassKeepDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class PassKeepLoginTest{
    private lateinit var db: PassKeepDatabase
    private lateinit var loginDao: LoginDao

    @Before
    fun setup(){
        val context=InstrumentationRegistry.getInstrumentation().targetContext
        db=Room.inMemoryDatabaseBuilder(context, PassKeepDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        loginDao=db.loginDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

//    @Test
//    @Throws(Exception::class)
//    fun insertData()= runBlocking {
//        val newData= Login("Gmail","Kshitij2212","KK2120")
//        loginDao.loginInsert(newData)
//        val returned= loginDao.getDetails("Gmail","Kshitij2212")
//        assertEquals(newData.userpw,returned?.userpw)
//    }

//    @Test
//    @Throws(Exception::class)
//    fun updateData()= runBlocking {
//        val oldData= Login("Gmail","Kshitij2212","KK2120")
//        loginDao.loginInsert(oldData)
//        loginDao.loginUpdate("Gmail","Kshitij2212","2120KK")
//        val returned= loginDao.getDetails("Gmail")
//        assertEquals("2120KK",returned?.userpw)
//    }

    @Test
    @Throws(Exception::class)
    fun getAll()= runBlocking {
        val newData= Login("Gmail","Kshitij2212","KK2120")
        loginDao.loginInsert(newData)
        loginDao.loginDelete("Gmail","Kshitij2212")
        val returned= loginDao.getListOfWebsites()
        assertEquals(0 ,returned.size)
    }
}