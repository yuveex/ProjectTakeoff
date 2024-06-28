package com.ubaya.projecttakeoff.view

import androidx.navigation.NavController
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    binding.navView.visibility = View.GONE
                    binding.bottomNav.visibility = View.GONE
                    supportActionBar?.hide()
                }
                R.id.registerFragment -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    binding.navView.visibility = View.GONE
                    binding.bottomNav.visibility = View.GONE
                    supportActionBar?.show()
                }
                else -> {
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    binding.navView.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.VISIBLE
                    supportActionBar?.show()
                }
            }
        }
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