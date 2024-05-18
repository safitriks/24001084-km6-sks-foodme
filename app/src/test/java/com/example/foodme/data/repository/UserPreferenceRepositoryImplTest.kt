package com.example.foodme.data.repository

import com.example.foodme.data.datasource.user.UserDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserPreferenceRepositoryImplTest {
    @MockK
    lateinit var userDataSource: UserDataSource
    private lateinit var userPreferenceRepository: UserPreferenceRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPreferenceRepository = UserPreferenceRepositoryImpl(userDataSource)
    }

    @Test
    fun isUsingGridMode() {
        every { userDataSource.isUsingGridMode() } returns true
        val actualResult = userPreferenceRepository.isUsingGridMode()
        verify { userDataSource.isUsingGridMode() }
        assertEquals(true, actualResult)
    }

    @Test
    fun setUsingGridMode() {
        every { userDataSource.setUsingGridMode(any()) } returns Unit
        userPreferenceRepository.setUsingGridMode(true)
        verify { userDataSource.setUsingGridMode(any()) }
    }
}
