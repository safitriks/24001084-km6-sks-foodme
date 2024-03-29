package com.example.foodme.data.datasource.category

import com.example.foodme.data.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}