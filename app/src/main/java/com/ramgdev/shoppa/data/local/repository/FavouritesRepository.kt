package com.ramgdev.shoppa.data.local.repository

import androidx.lifecycle.LiveData
import com.ramgdev.shoppa.data.local.favourite.FavouriteEntity
import com.ramgdev.shoppa.data.remote.model.favorite.Favorite

interface FavouritesRepository {
    suspend fun insertToWishlist(wishlist: Favorite)
    fun getWishlist(): LiveData<List<FavouriteEntity>>
    fun inWishlist(id: Int): LiveData<Boolean>
    fun getOneWishlistItem(id: Int): LiveData<FavouriteEntity?>
    suspend fun deleteOneWishlist(wishlist: Favorite)
    suspend fun deleteAllWishlist()
}