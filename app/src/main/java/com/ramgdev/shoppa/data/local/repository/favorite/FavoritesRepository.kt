package com.ramgdev.shoppa.data.local.repository.favorite

import androidx.lifecycle.LiveData
import com.ramgdev.shoppa.data.local.db.ProductsDatabase
import com.ramgdev.shoppa.data.local.entity.FavoritesEntity

class FavoritesRepository(
    private val productsDatabase: ProductsDatabase
) {
    suspend fun insertToFavorites(favoritesEntity: FavoritesEntity) {
        productsDatabase.favoritesDao().insertToFavorites(favoritesEntity)
    }

    fun getFavoriteItems(): LiveData<List<FavoritesEntity>> {
        return productsDatabase.favoritesDao().getFavoriteItems()
    }

    fun inFavorites(id: Int): Boolean {
        return productsDatabase.favoritesDao().inFavoritesList(id)
    }

    suspend fun deleteOneFavoriteItem(favoritesEntity: FavoritesEntity) {
        productsDatabase.favoritesDao().deleteOneFavoriteItem(favoritesEntity)
    }

    suspend fun deleteAllFavoriteItems() {
        productsDatabase.favoritesDao().deleteAllFavoriteItems()
    }
}