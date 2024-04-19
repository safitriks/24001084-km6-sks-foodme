package com.example.foodme.data.repository

import com.example.foodme.data.mapper.toCategories
import com.example.foodme.data.datasource.category.CategoryDataSource
import com.example.foodme.data.model.Category
import com.example.foodme.utils.ResultWrapper
import com.example.foodme.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(
    private val dataSource: CategoryDataSource
) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow{ dataSource.getCategories().data.toCategories()}
    }
}