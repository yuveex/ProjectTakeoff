package com.ubaya.projecttakeoff.view

import androidx.navigation.NavController
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ubaya.projecttakeoff.R
import com.ubaya.projecttakeoff.databinding.ActivityMainBinding
import com.ubaya.projecttakeoff.databinding.FragmentLoginBinding
import com.ubaya.projecttakeoff.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//
        navController = (supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment).navController

        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)


        binding.bottomNav.setupWithNavController(navController)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    fun getUserViewModel(): UserViewModel{
        return userViewModel
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)  || super.onSupportNavigateUp()
    }

//    fun clearUserViewModel(){
//        viewModelStore.clear()
//    }
}