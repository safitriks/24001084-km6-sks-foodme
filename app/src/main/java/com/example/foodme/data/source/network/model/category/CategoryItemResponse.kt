package com.example.foodme.data.source.network.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryItemResponse(
    @SerializedName("nama")
    val name: String?,
    @SerializedName("image_url")
    val imgUrl: String?,
)
