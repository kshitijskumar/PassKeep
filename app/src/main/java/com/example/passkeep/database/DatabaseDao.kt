package com.example.passkeep.database

import androidx.room.*

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun loginInsert(details: Login)

    @Query("UPDATE login_table SET password=:newPw WHERE website LIKE :webKey AND username LIKE :userName")
    suspend fun loginUpdate(webKey: String,userName: String,newPw: String)


    @Query("SELECT * FROM login_table ORDER BY id")
    suspend fun getListOfWebsites(): List<Login>

    @Query("DELETE FROM login_table WHERE website LIKE :webKey AND username LIKE :userName")
    suspend fun loginDelete(webKey: String,userName: String)


}

@Dao
interface AuthenticationDao{

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAuthKey(key: AuthenticationKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDefaultKey(key: AuthenticationKey)

    @Query("SELECT authentication_key FROM authentication_table")
    suspend fun checkKey(): String
}