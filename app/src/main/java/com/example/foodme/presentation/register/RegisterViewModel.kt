package com.example.foodme.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodme.data.repository.UserRepository
import com.example.foodme.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    fun doRegister(fullName: String, email: String, password: String) =
        repository.doRegister(
            fullName = fullName,
            email = email,
            password = password
        ).asLiveData(Dispatchers.IO)
}