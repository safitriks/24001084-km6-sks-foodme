package com.example.foodme.data.datasource.auth

import com.example.foodme.data.source.firebase.FirebaseService
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceImplTest {
    @MockK
    lateinit var service: FirebaseService
    private lateinit var dataSource: FirebaseAuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSourceImpl(service)
    }

    @Test
    fun doLogin() {
        runTest {
            coEvery { dataSource.doLogin(any(), any()) } returns true
            val actualResult = service.doLogin("safitri@gmail.com", "123456789")
            coVerify { dataSource.doLogin(any(), any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val name = "Safitri"
            val email = "safitri@gmail.com"
            val password = "123456789"
            coEvery { service.doRegister(name, email, password) } returns true
            val result = dataSource.doRegister(name, email, password)
            coVerify { service.doRegister(name, email, password) }
            assertEquals(true, result)
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            coEvery { dataSource.updateProfile(any()) } returns true
            val actualResult = service.updateProfile("Safitrii")
            coVerify { dataSource.updateProfile(any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            coEvery { dataSource.updatePassword(any()) } returns true
            val actualResult = service.updatePassword("12345678")
            coVerify { dataSource.updatePassword(any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            coEvery { dataSource.updateEmail(any()) } returns true
            val actualResult = service.updateEmail("safitrii@gmail.com")
            coVerify { dataSource.updateEmail(any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            coEvery { service.doLogout() } returns true
            val result = dataSource.doLogout()
            assertTrue(result)
            coVerify { service.doLogout() }
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { dataSource.isLoggedIn() } returns true
            val actualResult = service.isLoggedIn()
            verify { dataSource.isLoggedIn() }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val firebaseUser = mockk<FirebaseUser>()
            every { firebaseUser.displayName } returns "Safitri"
            every { firebaseUser.email } returns "safitri@gmail.com"
            every { service.getCurrentUser() } returns firebaseUser

            val result = dataSource.getCurrentUser()
            assertEquals("Safitri", result?.fullName)
            assertEquals("safitri@gmail.com", result?.email)
            verify { service.getCurrentUser() }
        }
    }
}
