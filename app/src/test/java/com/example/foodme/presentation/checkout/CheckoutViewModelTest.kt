package com.example.foodme.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodme.data.model.User
import com.example.foodme.data.repository.CartRepository
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.repository.UserRepository
import com.example.foodme.tools.MainCoroutineRule
import com.example.foodme.tools.getOrAwaitValue
import com.example.foodme.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CheckoutViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { cartRepository.getCheckoutData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Triple(
                            mockk(relaxed = true), mockk(relaxed = true), 0.0,
                        ),
                    ),
                )
            }

        viewModel =
            spyk(
                CheckoutViewModel(
                    cartRepository,
                    menuRepository,
                    userRepository,
                ),
            )
    }

    @Test
    fun getCheckoutData() {
    }

    @Test
    fun getCurrentUser() {
        val mockUser = mockk<User>()
        every { userRepository.getCurrentUser() } returns mockUser
        viewModel.getCurrentUser()
        verify { userRepository.getCurrentUser() }
    }

    @Test
    fun checkoutCart() {
        val mockUsername = "safitri"
        every { userRepository.getCurrentUser()?.fullName } returns mockUsername
        val expected = ResultWrapper.Success(true)
        coEvery { menuRepository.createOrder(any(), any()) } returns
            flow {
                emit(expected)
            }
        val result = viewModel.checkoutCart().getOrAwaitValue()
        assertEquals(expected, result)
    }

    @Test
    fun deleteAllCart() {
    }
}
