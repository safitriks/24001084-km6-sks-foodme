package com.example.foodme.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodme.R
import com.example.foodme.data.model.Profile
import com.example.foodme.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    fun getCurrentUser() = repository.getCurrentUser()

    fun doLogout() = repository.doLogout().asLiveData(Dispatchers.IO)
    val isEditMode = MutableLiveData(false)
    val buttonText = MutableLiveData("Edit")

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun changeIcon(): Int {
        val currentValue = isEditMode.value ?: false
        return if (!currentValue) {
            R.drawable.ic_edit
        } else{
            R.drawable.ic_save
        }
    }
}