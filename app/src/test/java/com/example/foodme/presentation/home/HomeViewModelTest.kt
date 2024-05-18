package com.example.foodme.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodme.data.repository.CategoryRepository
import com.example.foodme.data.repository.MenuRepository
import com.example.foodme.data.repository.UserPreferenceRepository
import com.example.foodme.tools.MainCoroutineRule
import com.example.foodme.tools.getOrAwaitValue
import com.example.foodme.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var userPreferenceRepository: UserPreferenceRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { userPreferenceRepository.isUsingGridMode() } returns true
        every { userPreferenceRepository.setUsingGridMode(any()) } returns Unit
        viewModel =
            spyk(
                HomeViewModel(
                    categoryRepository,
                    menuRepository,
                    userPreferenceRepository,
                ),
            )
    }

//    @Test
//    fun `isUsingGridMode should return true`() =
//        runTest {
//            val expectedValue = true
//            val actualValue = viewModel.isUsingGridMode.value
//            assertEquals(expectedValue, actualValue)
//        }

//    @Test
//    fun getListMode() {
//    }

//    @Test
//    fun changeListMode() {
//        runTest {
//            val currentValue = viewModel.isUsingGridMode.value ?: false
//            val expectedNewValue = !currentValue
//            viewModel.changeListMode()
//            assertEquals(expectedNewValue, viewModel.isUsingGridMode.value)
//        }
//    }

    @Test
    fun getMenus() {
        every { menuRepository.getMenus() } returns
            flow {
                emit(
                    ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))),
                )
            }
        val result = viewModel.getMenus().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun getCategory() {
        every { categoryRepository.getCategories() } returns
            flow {
                emit(
                    ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))),
                )
            }
        val result = viewModel.getCategory().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }
}
