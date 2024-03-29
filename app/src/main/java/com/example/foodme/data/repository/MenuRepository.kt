package com.example.foodme.data.repository

import com.example.foodme.data.datasource.menu.MenuDataSource
import com.example.foodme.data.model.Menu

interface MenuRepository {
    fun getMenus(): List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(): List<Menu> = dataSource.getMenus()
}