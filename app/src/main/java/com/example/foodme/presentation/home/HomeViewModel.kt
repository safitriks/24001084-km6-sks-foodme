package com.example.foodme.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodme.data.repository.CategoryRepository
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.repository.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userPreferenceRepository: UserPreferenceRepository
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(userPreferenceRepository.isUsingGridMode())
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun getListMode(): Int {
        return if (userPreferenceRepository.isUsingGridMode()) 1 else 0
    }

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        userPreferenceRepository.setUsingGridMode(!currentValue)
    }

    fun getMenus(categoryName: String? = null) =
        menuRepository.getMenus(categoryName).asLiveData(Dispatchers.IO)
    fun getCategory() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)
}