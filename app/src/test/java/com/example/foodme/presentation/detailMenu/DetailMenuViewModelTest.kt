package com.example.foodme.presentation.detailMenu

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodme.data.model.Menu
import com.example.foodme.data.repository.CartRepository
import com.example.foodme.tools.MainCoroutineRule
import com.example.foodme.tools.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailMenuViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var extras: Bundle

    private lateinit var viewModel: DetailMenuViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val expectedCatalog =
            Menu(
                "1",
                "img",
                25000.0,
                "format",
                "bbbbbb",
                "ccccc",
                "sdfsdfsdfdf",
                "aaaa",
            )
        every { extras.getParcelable<Menu>(DetailMenuActivity.EXTRA_MENU) } returns expectedCatalog

        viewModel =
            spyk(
                DetailMenuViewModel(
                    extras, cartRepository,
                ),
            )
    }

    @Test
    fun getMenu() {
    }

    @Test
    fun getMenuCountLiveData() {
        val menuCount = viewModel.menuCountLiveData.getOrAwaitValue()
        assertEquals(0, menuCount)
    }

    @Test
    fun getPriceLiveData() {
        val price = viewModel.priceLiveData.getOrAwaitValue()
        assertEquals(0.0, price, 0.0)
    }

    @Test
    fun add() {
        viewModel.add()
        assertEquals(1, viewModel.menuCountLiveData.getOrAwaitValue())
        assertEquals(25000.0, viewModel.priceLiveData.getOrAwaitValue(), 0.0)
    }

    @Test
    fun minus() {
        viewModel.add()
        viewModel.minus()
        assertEquals(0, viewModel.menuCountLiveData.getOrAwaitValue())
        assertEquals(0.0, viewModel.priceLiveData.getOrAwaitValue(), 0.0)
    }

    @Test
    fun getLocationUrl() {
    }

//    @Test
//    fun addToCart() {
//        runTest {
//            val catalog =
//                Menu(
//                    "1",
//                    "img",
//                    25000.0,
//                    "format",
//                    "bbbbbb",
//                    "ccccc",
//                    "sdfsdfsdfdf",
//                    "aaaa",
//                )
//            coEvery { cartRepository.createCart(catalog, any()) } returns
//                flow {
//                    emit(ResultWrapper.Success(true))
//                }
//
//            viewModel.add()
//            val result = viewModel.addToCart().getOrAwaitValue()
//
//            assertTrue(result is ResultWrapper.Success)
//            assertEquals(true, (result as ResultWrapper.Success).payload)
//        }
//    }
}
