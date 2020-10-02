package com.example.passkeep.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authentication_table")
data class AuthenticationKey(
    @PrimaryKey(autoGenerate = false) val _id: Int,
    @ColumnInfo(name = "authentication_key") var authKey: String?
)

//@Entity(tableName = "login_table")
//data class Login(
//    @PrimaryKey(autoGenerate = true) val _id: Int,
//    @ColumnInfo(name = "website") val site : String,
//    @ColumnInfo(name = "username") val userid : String,
//    @ColumnInfo(name = "password") var userpw: String,
//)

@Entity(tableName = "login_table")
class Login(
    @ColumnInfo(name = "website") val site: String,
    @ColumnInfo(name = "username") val userid : String,
    @ColumnInfo(name = "password") var userpw: String
){
    @PrimaryKey(autoGenerate = true) var id: Int=0

}
