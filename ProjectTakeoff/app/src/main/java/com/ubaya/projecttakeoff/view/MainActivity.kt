package com.ubaya.projecttakeoff.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ubaya.projecttakeoff.R
import com.ubaya.projecttakeoff.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    fun getUserViewModel(): UserViewModel{
        return userViewModel
    }

//    fun clearUserViewModel(){
//        viewModelStore.clear()
//    }
}