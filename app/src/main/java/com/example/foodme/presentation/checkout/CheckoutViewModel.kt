package com.example.foodme.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodme.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {

    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun deleteAllCart() {
        viewModelScope.launch (Dispatchers.IO){
            cartRepository.deleteAll().collect()
        }
    }
}