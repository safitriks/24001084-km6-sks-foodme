package com.example.foodme.data.datasource.menu

import com.example.foodme.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodme.data.source.network.model.checkout.CheckoutResponse
import com.example.foodme.data.source.network.model.menu.MenuResponse

interface MenuDataSource {
    suspend fun getMenus(categoryName : String? = null): MenuResponse
    suspend fun createOrder(payload : CheckoutRequestPayload) : CheckoutResponse
}