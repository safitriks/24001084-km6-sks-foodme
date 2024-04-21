package com.example.foodme.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodme.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    fun getCurrentUser() = repository.getCurrentUser()
    fun updateUsername(newName: String) = repository.updateProfile(
        fullName = newName
    ).asLiveData(Dispatchers.IO)

    fun doLogout() = repository.doLogout().asLiveData(Dispatchers.IO)
}