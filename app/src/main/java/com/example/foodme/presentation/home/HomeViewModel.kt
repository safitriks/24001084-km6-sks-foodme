package com.example.foodme.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodme.data.repository.CategoryRepository
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.source.local.pref.UserPreference

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userPref: UserPreference
) : ViewModel() {

    private val _isUsingGridMode = MutableLiveData(userPref.isUsingDarkMode())
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        userPref.setUsingDarkMode(!currentValue)
    }


    fun getMenus() = menuRepository.getMenus()
    fun getCategories() = categoryRepository.getCategories()
}