package com.example.foodme.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.foodme.R
import com.example.foodme.data.datasource.auth.FirebaseAuthDataSourceImpl
import com.example.foodme.data.repository.UserRepositoryImpl
import com.example.foodme.data.source.firebase.FirebaseService
import com.example.foodme.data.source.firebase.FirebaseServiceImpl
import com.example.foodme.databinding.ActivityMainBinding
import com.example.foodme.presentation.login.LoginActivity
import com.example.foodme.presentation.profile.ProfileViewModel
import com.example.foodme.utils.GenericViewModelFactory
import com.example.foodme.utils.proceedWhen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels() {
        val service : FirebaseService = FirebaseServiceImpl()
        val dataSource = FirebaseAuthDataSourceImpl(service)
        val repo = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(MainViewModel(repo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNav()
    }

    private fun setBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.menu_tab_profile -> {
                    if (!viewModel.isLogin()) {
                        navigateToLogin()
                        controller.navigate(R.id.menu_tab_home)
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}