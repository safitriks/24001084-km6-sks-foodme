package com.example.foodme.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodme.R
import com.example.foodme.data.model.Profile

class ProfileViewModel : ViewModel() {

    val profileData = MutableLiveData(
        Profile(
            name = "Safitri Kurnia Suardini",
            username = "safit",
            email = "safitri.suardini@gmail.com",
            profileImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxbz8S46qH4I4g7PacDGHeZuKICCu7zk3zlA&usqp=CAU"
        )
    )

    val isEditMode = MutableLiveData(false)

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