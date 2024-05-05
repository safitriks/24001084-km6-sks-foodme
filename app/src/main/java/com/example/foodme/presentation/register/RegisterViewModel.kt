package com.example.foodme.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodme.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun doRegister(
        fullName: String,
        email: String,
        password: String,
    ) = repository.doRegister(
        fullName = fullName,
        email = email,
        password = password,
    ).asLiveData(Dispatchers.IO)
}
