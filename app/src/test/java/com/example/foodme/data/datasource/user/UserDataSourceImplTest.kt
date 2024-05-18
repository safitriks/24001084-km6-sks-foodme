package com.example.foodme.data.datasource.user

import com.example.foodme.data.source.local.pref.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserDataSourceImplTest {
    @MockK
    lateinit var up: UserPreference

    lateinit var ds: UserDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = UserDataSourceImpl(up)
    }

    @Test
    fun isUsingGridMode() {
        every { up.isUsingGridMode() } returns true
        val actualResult = ds.isUsingGridMode()
        verify { up.isUsingGridMode() }
        assertEquals(true, actualResult)
    }

    @Test
    fun setUsingGridMode() {
        every { up.setUsingGridMode(any()) } returns Unit
        ds.setUsingGridMode(true)
        verify { up.setUsingGridMode(any()) }
    }
}
