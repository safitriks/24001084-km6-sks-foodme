package com.example.foodme.data.datasource.menu

import com.example.foodme.data.model.Menu

interface MenuDataSource {
    fun getMenus(): List<Menu>
}