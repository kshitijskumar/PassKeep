package com.example.passkeep.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [AuthenticationKey::class, Login::class],version = 1, exportSchema = false)
abstract class PassKeepDatabase : RoomDatabase(){
    abstract val loginDao: LoginDao
    abstract val authKeyDao: AuthenticationDao


    companion object{
        @Volatile
        private var INSTANCE: PassKeepDatabase?= null

        fun getInstance(context: Context): PassKeepDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    instance=Room.databaseBuilder(
                                                context.applicationContext,
                                                PassKeepDatabase::class.java,
                                                "pass_keep_database"
                                                 )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =instance


                }
                return instance
            }
        }
    }
}

