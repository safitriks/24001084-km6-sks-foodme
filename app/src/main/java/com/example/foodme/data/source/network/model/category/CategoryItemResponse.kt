package com.example.foodme.data.source.network.model.category

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoryItemResponse(
    @SerializedName("nama")
    val name: String?,
    @SerializedName("image_url")
    val imgUrl: String?
)