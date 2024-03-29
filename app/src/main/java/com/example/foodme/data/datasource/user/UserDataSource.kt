package com.example.foodme.data.datasource.user

interface UserDataSource {
    fun isUsingDarkMode(): Boolean
    fun setUsingDarkMode(isUsingDarkMode: Boolean)
}