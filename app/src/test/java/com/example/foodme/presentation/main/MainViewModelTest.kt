package com.example.foodme.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodme.data.model.User
import com.example.foodme.data.repository.UserRepository
import com.example.foodme.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                MainViewModel(
                    repo,
                ),
            )
    }

    @Test
    fun isLogin() {
        every { repo.isLoggedIn() } returns true
        val result = viewModel.isLogin()
        assertEquals(true, result)
        verify { repo.isLoggedIn() }
    }

    @Test
    fun getCurrentUsername() {
        val mockUser = mockk<User>()
        every { repo.getCurrentUser() } returns mockUser
        viewModel.getCurrentUsername()
        verify { repo.getCurrentUser() }
    }
}
