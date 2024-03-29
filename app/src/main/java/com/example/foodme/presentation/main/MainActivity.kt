package com.example.foodme.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodme.R
import com.example.foodme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }


}