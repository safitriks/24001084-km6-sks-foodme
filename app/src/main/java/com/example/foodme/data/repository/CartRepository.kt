package com.example.foodme.data.repository

import com.example.foodme.data.datasource.cart.CartDataSource
import com.example.foodme.data.mapper.toCartEntity
import com.example.foodme.data.mapper.toCartList
import com.example.foodme.data.model.Cart
import com.example.foodme.data.model.Menu
import com.example.foodme.data.model.PriceItem
import com.example.foodme.data.source.local.database.entity.CartEntity
import com.example.foodme.utils.ResultWrapper
import com.example.foodme.utils.proceed
import com.example.foodme.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.IllegalStateException


interface CartRepository {
    fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>>
    fun getCheckoutData(): Flow<ResultWrapper<Triple<List<Cart>, List<PriceItem>, Double>>>
    fun createCart(
        menu: Menu,
        quantity: Int,
        notes: String? = null
    ): Flow<ResultWrapper<Boolean>>

    fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
    fun deleteAll(): Flow<ResultWrapper<Boolean>>
}

class CartRepositoryImpl(private val cartDataSource: CartDataSource) : CartRepository {

    override fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>> {
        return cartDataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf { it.menuPrice * it.itemQuantity }
                    Pair(result, totalPrice)
                }
            }.map {
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun getCheckoutData(): Flow<ResultWrapper<Triple<List<Cart>, List<PriceItem>, Double>>> {
        return cartDataSource.getAllCarts()
            .map {
                //mapping into cart list and sum the total price
                proceed {
                    val result = it.toCartList()
                    val priceItemList =
                        result.map { PriceItem(it.menuName, it.menuPrice * it.itemQuantity) }
                    val totalPrice = priceItemList.sumOf { it.total }
                    Triple(result, priceItemList, totalPrice)
                }
            }.map {
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun createCart(
        menu: Menu,
        quantity: Int,
        notes: String?
    ): Flow<ResultWrapper<Boolean>> {
        return menu.id?.let { menuId ->
            proceedFlow {
                val affectedRow = cartDataSource.insertCart(
                    CartEntity(
                        menuId = menuId,
                        itemQuantity = quantity,
                        menuName = menu.name,
                        menuImgUrl = menu.imgUrl,
                        menuPrice = menu.price,
                        itemNotes = notes
                    )
                )
                delay(2000)
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Menu ID not found")))
        }
    }

    override fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
        } else {
            proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            cartDataSource.deleteAll()
            true
        }
    }
}