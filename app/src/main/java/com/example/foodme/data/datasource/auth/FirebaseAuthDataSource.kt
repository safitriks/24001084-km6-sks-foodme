package com.example.foodme.data.datasource.auth

import com.example.foodme.data.mapper.toUser
import com.example.foodme.data.model.User
import com.example.foodme.data.source.firebase.FirebaseService
import com.google.firebase.auth.auth

interface FirebaseAuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        fullName: String,
        email: String,
        password: String,
    ): Boolean

    suspend fun updateProfile(fullName: String? = null): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    suspend fun updateEmail(newEmail: String): Boolean

    suspend fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): User?
}

class FirebaseAuthDataSourceImpl(private val service: FirebaseService) : FirebaseAuthDataSource {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        return service.doLogin(email, password)
    }

    override suspend fun doRegister(
        fullName: String,
        email: String,
        password: String,
    ): Boolean {
        return service.doRegister(fullName, email, password)
    }

    override suspend fun updateProfile(fullName: String?): Boolean {
        return service.updateProfile(fullName)
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        return service.updatePassword(newPassword)
    }

    override suspend fun updateEmail(newEmail: String): Boolean {
        return service.updateEmail(newEmail)
    }

    override suspend fun doLogout(): Boolean {
        return service.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return service.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return service.getCurrentUser().toUser()
    }
}
