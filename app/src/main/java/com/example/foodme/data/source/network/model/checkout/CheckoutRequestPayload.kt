package com.example.foodme.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutRequestPayload(
    @SerializedName("username")
    val username: String,
    @SerializedName("total")
    val total: Double,
    @SerializedName("orders")
    val orders: List<CheckoutItemPayload>,
)
