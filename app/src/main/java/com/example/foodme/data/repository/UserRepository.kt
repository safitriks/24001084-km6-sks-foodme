package com.example.foodme.data.repository

import com.example.foodme.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodme.data.model.User
import com.example.foodme.utils.ResultWrapper
import com.example.foodme.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    @Throws(exceptionClasses = [Exception::class])
    fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>>

    @Throws(exceptionClasses = [Exception::class])
    fun doRegister(email: String, fullName: String, password: String): Flow<ResultWrapper<Boolean>>
    fun updateProfile(fullName: String? = null): Flow<ResultWrapper<Boolean>>
    fun doLogout(): Flow<ResultWrapper<Boolean>>
    fun isLoggedIn(): Boolean
    fun getCurrentUser(): User?
}

class UserRepositoryImpl(private val dataSource: FirebaseAuthDataSource) : UserRepository {

    override fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doLogin(email, password) }
    }

    override fun doRegister(
        email: String,
        fullName: String,
        password: String
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doRegister(email, fullName, password) }
    }

    override fun updateProfile(fullName: String?): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.updateProfile(fullName) }

    }

    override fun doLogout(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.doLogout() }
    }

    override fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return dataSource.getCurrentUser()
    }
}