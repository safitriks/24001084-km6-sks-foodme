package com.example.foodme.data.datasource.menu

import com.example.foodme.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodme.data.source.network.model.checkout.CheckoutResponse
import com.example.foodme.data.source.network.model.menu.MenuResponse
import com.example.foodme.data.source.network.service.RestaurantApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuApiDataSourceTest {
    @MockK
    lateinit var service: RestaurantApiService

    private lateinit var ds: MenuDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = MenuApiDataSource(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<MenuResponse>(relaxed = true)
            coEvery { service.getMenus() } returns mockResponse
            val actualResult = ds.getMenus()
            coVerify { service.getMenus() }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestPayload>(relaxed = true)
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResponse = ds.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(actualResponse, mockResponse)
        }
    }
}
