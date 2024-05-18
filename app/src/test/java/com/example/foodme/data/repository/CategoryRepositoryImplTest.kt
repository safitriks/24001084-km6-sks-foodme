package com.example.foodme.data.repository

import app.cash.turbine.test
import com.example.foodme.data.datasource.category.CategoryDataSource
import com.example.foodme.data.source.network.model.category.CategoriesResponse
import com.example.foodme.data.source.network.model.category.CategoryItemResponse
import com.example.foodme.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {
    @MockK
    lateinit var ds: CategoryDataSource

    lateinit var repo: CategoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CategoryRepositoryImpl(ds)
    }

    @Test
    fun `get categories loading`() {
        val c1 =
            CategoryItemResponse(
                name = "psu",
                imgUrl = "awfawf",
            )
        val c2 =
            CategoryItemResponse(
                name = "psuwaf",
                imgUrl = "awfaafawfwawf",
            )
        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { ds.getCategories() } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { ds.getCategories() }
            }
        }
    }

    @Test
    fun `get categories success`() {
        val c1 =
            CategoryItemResponse(
                name = "psu",
                imgUrl = "awfawf",
            )
        val c2 =
            CategoryItemResponse(
                name = "psuwaf",
                imgUrl = "awfaafawfwawf",
            )
        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { ds.getCategories() } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { ds.getCategories() }
            }
        }
    }

    @Test
    fun `get categories error`() {
        runTest {
            coEvery { ds.getCategories() } throws IllegalStateException("Mock Error")
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { ds.getCategories() }
            }
        }
    }

    @Test
    fun `get categories empty`() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { ds.getCategories() } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { ds.getCategories() }
            }
        }
    }
}
