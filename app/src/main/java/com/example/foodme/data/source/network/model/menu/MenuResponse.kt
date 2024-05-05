package com.example.foodme.data.source.network.model.menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MenuResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<MenuItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
