package com.example.foodme.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id: String? = UUID.randomUUID().toString(),
    var imgUrl: String,
    var price: Double,
    var formattedprice: String,
    var details: String,
    var name: String,
    var location: String,
    var locationUrl: String,
) : Parcelable
