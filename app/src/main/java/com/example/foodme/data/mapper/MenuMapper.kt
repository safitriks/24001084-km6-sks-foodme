package com.example.foodme.data.mapper

import com.example.foodme.data.model.Menu
import com.example.foodme.data.source.network.model.menu.MenuItemResponse

fun MenuItemResponse?.toMenu() =
    Menu(
        name = this?.nama.orEmpty(),
        price = this?.harga?.toDouble() ?: 0.0,
        imgUrl = this?.imageUrl.orEmpty(),
        location = this?.alamatResto.orEmpty(),
        formattedprice = this?.hargaFormat.orEmpty(),
        details = this?.detail.orEmpty(),
        locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
    )

fun Collection<MenuItemResponse>?.toMenus() =
    this?.map {
        it.toMenu()
    } ?: listOf()
