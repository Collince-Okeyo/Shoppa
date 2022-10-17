package com.ramgdev.shoppa.data.local.repository

import androidx.lifecycle.LiveData
import com.ramgdev.shoppa.data.local.favourite.FavouriteEntity
import com.ramgdev.shoppa.data.remote.model.favorite.FavoriteEntity

interface FavouritesRepository {
    suspend fun insertToWishlist(wishlist: FavouriteEntity)
    fun getWishlist(): LiveData<List<FavouriteEntity>>
    fun inWishlist(id: Int): LiveData<Boolean>
    fun getOneWishlistItem(id: Int): LiveData<FavouriteEntity?>
    suspend fun deleteOneWishlist(wishlist: FavoriteEntity)
    suspend fun deleteAllWishlist()
}