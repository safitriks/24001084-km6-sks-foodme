package com.example.foodme.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutItemPayload(
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val qty: Int,
    @SerializedName("price")
    val price: Double,
)
