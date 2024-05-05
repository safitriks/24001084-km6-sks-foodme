package com.example.foodme.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodme.data.repository.CartRepository
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val menuRepository: MenuRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun getCurrentUser() = userRepository.getCurrentUser()

    fun checkoutCart() =
        menuRepository.createOrder(
            checkoutData.value?.payload?.first.orEmpty(),
            getCurrentUser()?.fullName.orEmpty(),
        ).asLiveData(Dispatchers.IO)

    fun deleteAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAll().collect()
        }
    }
}
