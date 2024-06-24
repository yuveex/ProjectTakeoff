package com.ubaya.projecttakeoff.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey (autoGenerate = true)
    var id: String,

    @ColumnInfo(name = "title")
    var email: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "first_name")
    var first_name: String,
    @ColumnInfo(name = "last_name")
    var last_name: String,
    @ColumnInfo(name = "profile_picture")
    var profile_picture: String
)
