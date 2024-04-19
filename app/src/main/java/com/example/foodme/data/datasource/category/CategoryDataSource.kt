package com.example.foodme.data.datasource.category

import com.example.foodme.data.source.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategories(): CategoriesResponse
}