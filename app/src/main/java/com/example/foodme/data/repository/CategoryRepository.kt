package com.example.foodme.data.repository

import com.example.foodme.data.datasource.category.CategoryDataSource
import com.example.foodme.data.model.Category

interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategories()
}