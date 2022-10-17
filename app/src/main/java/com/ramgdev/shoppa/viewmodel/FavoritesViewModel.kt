package com.ramgdev.shoppa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramgdev.shoppa.data.local.entity.FavoritesEntity
import com.ramgdev.shoppa.data.local.mapper.toFavEntity
import com.ramgdev.shoppa.data.local.repository.favorite.FavoritesRepository
import com.ramgdev.shoppa.data.remote.model.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    val favItems = repository.getFavoriteItems()

    fun addToFav(product: Products) {
        viewModelScope.launch {
            repository.insertToFavorites(product.toFavEntity())
        }
    }

    fun deleteOneItemFromFav(product: FavoritesEntity) {
        viewModelScope.launch {
            repository.deleteOneFavoriteItem(product)
        }
    }

    fun deleteAllFav() {
        viewModelScope.launch {
            repository.deleteAllFavoriteItems()
        }
    }

    fun inFavorites(productsDetails: Products): Boolean {
        return repository.inFavorites(productsDetails.id)
    }
}