package com.example.foodme.data.repository

import app.cash.turbine.test
import com.example.foodme.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodme.data.model.User
import com.example.foodme.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class UserRepositoryImplTest {
    @MockK
    lateinit var dataSource: FirebaseAuthDataSource

    lateinit var repo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = UserRepositoryImpl(dataSource)
    }

    @Test
    fun doLogin_success() {
        val email = "safitri@@gmail.com"
        val password = "123456789"
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doLogin_loading() {
        val email = "safitri@@gmail.com"
        val password = "123456789"
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doLogin_error() {
        val email = "safitri@@gmail.com"
        val password = "123456789"
        coEvery { dataSource.doLogin(any(), any()) } throws IOException("Login failed")
        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_success() {
        val fullName = "Safitri"
        val email = "safitri@@gmail.com"
        val password = "123456789"

        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repo.doRegister(fullName, email, password).map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_loading() {
        val fullName = "Safitri"
        val email = "safitri@@gmail.com"
        val password = "123456789"

        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repo.doRegister(fullName, email, password).map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun doRegister_error() {
        val fullName = "Safitri"
        val email = "safitri@@gmail.com"
        val password = "123456789"

        coEvery { dataSource.doRegister(any(), any(), any()) } throws IOException("Register failed")
        runTest {
            repo.doRegister(fullName, email, password).map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun updateProfile_success() {
        val fullName = "Safitri"

        coEvery { dataSource.updateProfile(any()) } returns true
        runTest {
            repo.updateProfile(fullName).map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { dataSource.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updateProfile_loading() {
        val fullName = "Safitri"

        coEvery { dataSource.updateProfile(any()) } returns true
        runTest {
            repo.updateProfile(fullName).map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updateProfile_error() {
        val fullName = "Safitri"

        coEvery { dataSource.updateProfile(any()) } throws IOException("update Profile error")
        runTest {
            repo.updateProfile(fullName).map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.updateProfile(any()) }
            }
        }
    }

//    @Test
//    fun doLogout() {
//        runTest {
//            every { dataSource.doLogout() } returns true
//            val actualResult = repo.doLogout()
//            verify { dataSource.doLogout() }
//            assertEquals(true, actualResult)
//        }
//    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { dataSource.isLoggedIn() } returns true
            val actualResult = repo.isLoggedIn()
            verify { dataSource.isLoggedIn() }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val currentUser = mockk<User>()
            every { currentUser.fullName } returns "Safitri"
            every { currentUser.email } returns "safitri@gmail.com"
            every { dataSource.getCurrentUser() } returns currentUser

            val result = dataSource.getCurrentUser()
            assertEquals("Safitri", result?.fullName)
            assertEquals("safitri@gmail.com", result?.email)
            verify { repo.getCurrentUser() }
        }
    }
}
