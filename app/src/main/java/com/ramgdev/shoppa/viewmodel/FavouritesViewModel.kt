package com.ramgdev.shoppa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramgdev.shoppa.data.local.favourite.toEntity
import com.ramgdev.shoppa.data.local.repository.FavouritesRepository
import com.ramgdev.shoppa.data.remote.model.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: FavouritesRepository): ViewModel() {

    fun addToCart(product: Products) {
        viewModelScope.launch {
            repository.insertToWishlist(product.toEntity())
        }
    }

}