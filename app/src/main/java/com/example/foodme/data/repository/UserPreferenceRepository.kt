package com.example.foodme.data.repository

import com.example.foodme.data.datasource.user.UserDataSource

interface UserPreferenceRepository {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserPreferenceRepositoryImpl(private val userDataSource: UserDataSource) : UserPreferenceRepository {
    override fun isUsingGridMode(): Boolean {
        return userDataSource.isUsingGridMode()
    }

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        userDataSource.setUsingGridMode(isUsingGridMode)
    }
}
