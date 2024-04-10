package com.ubaya.projecttakeoff.model

data class User(
    var id: String,
    var email: String,
    var username: String,
    var first_name: String,
    var last_name: String,
    var password: String,
    var profile_picture: String
)
