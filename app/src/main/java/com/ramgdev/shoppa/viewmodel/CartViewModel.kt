package com.ramgdev.shoppa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramgdev.shoppa.data.local.entity.CartEntity
import com.ramgdev.shoppa.data.local.mapper.toCartEntity
import com.ramgdev.shoppa.data.local.repository.cart.CartRepository
import com.ramgdev.shoppa.data.remote.model.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
    ): ViewModel() {

    val cartItems = repository.getCartItems()

    fun addToCart(product: Products) {
        viewModelScope.launch {
            repository.insertToCart(product.toCartEntity())
        }
    }

    fun deleteOneItemFromCart(product: CartEntity) {
        viewModelScope.launch {
            repository.deleteOneCartItem(product)
        }
    }
}