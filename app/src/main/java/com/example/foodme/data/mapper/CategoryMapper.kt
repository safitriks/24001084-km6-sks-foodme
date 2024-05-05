package com.example.foodme.data.mapper

import com.example.foodme.data.model.Category
import com.example.foodme.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() =
    Category(
        name = this?.name.orEmpty(),
        imgUrl = this?.imgUrl.orEmpty(),
    )

fun Collection<CategoryItemResponse>?.toCategories() =
    this?.map {
        it.toCategory()
    } ?: listOf()
