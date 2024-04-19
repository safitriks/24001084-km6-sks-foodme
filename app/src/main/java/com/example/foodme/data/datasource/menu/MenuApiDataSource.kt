package com.example.foodme.data.datasource.menu

import com.example.foodme.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodme.data.source.network.model.checkout.CheckoutResponse
import com.example.foodme.data.source.network.model.menu.MenuResponse
import com.example.foodme.data.source.network.service.RestaurantApiService

class MenuApiDataSource (
    private val service : RestaurantApiService
    ) : MenuDataSource {
        override suspend fun getMenus(categoryName: String?): MenuResponse {
            return service.getMenus(categoryName)
        }
    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return  service.createOrder(payload)
    }
}