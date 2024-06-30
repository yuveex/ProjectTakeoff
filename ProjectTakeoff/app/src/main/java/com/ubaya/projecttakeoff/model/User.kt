package com.ubaya.projecttakeoff.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "first_name")
    var first_name: String,
    @ColumnInfo(name = "last_name")
    var last_name: String,
    @ColumnInfo(name = "profile_picture")
    var profile_picture: String,
    // Password is left empty and will not be retrieved. Defined only to be stored in DB
    @ColumnInfo(name = "password")
    var password: String? = null,
    @ColumnInfo(name = "salt")
    var salt: String? = null,
){
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0
}
