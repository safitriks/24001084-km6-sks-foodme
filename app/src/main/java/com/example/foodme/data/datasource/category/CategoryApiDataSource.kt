package com.example.foodme.data.datasource.category

import com.example.foodme.data.source.network.model.category.CategoriesResponse
import com.example.foodme.data.source.network.service.RestaurantApiService

class CategoryApiDataSource (
    private val service : RestaurantApiService
    ) : CategoryDataSource{
        override suspend fun getCategories(): CategoriesResponse {
            return service.getCategories()
        }
    }