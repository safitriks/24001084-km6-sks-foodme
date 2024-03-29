package com.example.foodme.presentation.detailMenu

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.foodme.data.model.Menu
import com.example.foodme.data.repository.CartRepository
import com.example.foodme.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalStateException

class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {

    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRA_MENU)
    private var url : String? = null

    val menuCountLiveData = MutableLiveData(0).apply {
        postValue(0)
    }

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value ?: 0) - 1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
        }
    }

    fun getLocationUrl(): Uri? {
        url = (menu?.locationUrl.orEmpty())
        return url?.let{ Uri.parse(it)}
    }

    fun addToCart(): LiveData<ResultWrapper<Boolean>> {
        return menu?.let {
            val quantity = menuCountLiveData.value ?: 0
            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Menu not found"))) }
    }
}