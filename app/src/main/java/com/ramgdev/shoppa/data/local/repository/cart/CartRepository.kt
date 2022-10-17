package com.ramgdev.shoppa.data.local.repository.cart

import androidx.lifecycle.LiveData
import com.ramgdev.shoppa.data.local.db.ProductsDatabase
import com.ramgdev.shoppa.data.local.entity.CartEntity

class CartRepository(
    private val productsDatabase: ProductsDatabase
) {
    suspend fun insertToCart(cartEntity: CartEntity) {
        productsDatabase.cartDao().insertToWishlist(cartEntity)
    }

    fun getCartItems(): LiveData<List<CartEntity>> {
        return productsDatabase.cartDao().getCartItems()
    }

    fun inCart(id: Int): LiveData<Boolean> {
        return productsDatabase.cartDao().inCartList(id)
    }

    fun getOneCartItemItem(id: Int): LiveData<CartEntity?> {
        return productsDatabase.cartDao().getOneCartItemItem(id)
    }

    suspend fun deleteOneCartItem(cartEntity: CartEntity) {
        productsDatabase.cartDao().deleteOneCartItem(cartEntity)
    }

    suspend fun deleteAllCartItems() {
        productsDatabase.cartDao().deleteAllCartItems()
    }
}