package com.example.foodme.presentation.main

import androidx.lifecycle.ViewModel
import com.example.foodme.data.repository.UserRepository

class MainViewModel(private val repo: UserRepository) : ViewModel() {
    fun isLogin() = repo.isLoggedIn()

    fun getCurrentUsername() = repo.getCurrentUser()
}
