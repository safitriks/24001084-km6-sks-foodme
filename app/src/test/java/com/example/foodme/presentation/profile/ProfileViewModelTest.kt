package com.example.foodme.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodme.data.model.User
import com.example.foodme.data.repository.UserRepository
import com.example.foodme.tools.MainCoroutineRule
import com.example.foodme.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    private lateinit var repository: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(repository))
    }

    @Test
    fun getCurrentUser() {
        val mockUser = mockk<User>()
        every { repository.getCurrentUser() } returns mockUser
        viewModel.getCurrentUser()
        verify { repository.getCurrentUser() }
    }

    @Test
    fun updateUsername() {
        every { repository.updateProfile(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.updateUsername("safit")
        verify { repository.updateProfile(any()) }
    }

    @Test
    fun doLogout() {
    }
}
