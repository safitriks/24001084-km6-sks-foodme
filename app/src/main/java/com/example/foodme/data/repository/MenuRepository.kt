package com.example.foodme.data.repository

import com.example.foodme.data.datasource.menu.MenuDataSource
import com.example.foodme.data.mapper.toMenus
import com.example.foodme.data.model.Cart
import com.example.foodme.data.model.Menu
import com.example.foodme.data.source.network.model.checkout.CheckoutItemPayload
import com.example.foodme.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodme.utils.ResultWrapper
import com.example.foodme.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(categoryName: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(
        menu: List<Cart>,
        username: String,
    ): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(categoryName: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenus(categoryName).data.toMenus()
        }
    }

    override fun createOrder(
        menu: List<Cart>,
        username: String,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(
                CheckoutRequestPayload(
                    username = username,
                    total = menu.sumOf { it.menuPrice * it.itemQuantity },
                    orders =
                        menu.map {
                            CheckoutItemPayload(
                                notes = it.itemNotes,
                                name = it.menuName,
                                qty = it.itemQuantity,
                                price = it.menuPrice,
                            )
                        },
                ),
            ).status ?: false
        }
    }
}
