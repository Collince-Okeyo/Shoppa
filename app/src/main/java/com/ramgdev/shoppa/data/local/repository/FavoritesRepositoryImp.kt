package com.ramgdev.shoppa.data.local.repository

import androidx.lifecycle.LiveData
import com.ramgdev.shoppa.data.local.favourite.FavoritesDao
import com.ramgdev.shoppa.data.local.favourite.FavouriteEntity
import com.ramgdev.shoppa.data.local.favourite.toEntity
import com.ramgdev.shoppa.data.remote.model.favorite.FavoriteEntity

class FavoritesRepositoryImp(
    private val wishlistDao: FavoritesDao
) : FavouritesRepository {
    override suspend fun insertToWishlist(wishlist: FavoriteEntity) {
        wishlistDao.insertToWishlist(wishlist.toEntity())
    }

    override fun getWishlist(): LiveData<List<FavouriteEntity>> {
        return wishlistDao.getWishlist()
    }

    override fun inWishlist(id: Int): LiveData<Boolean> {
        return wishlistDao.inWishlist(id)
    }

    override fun getOneWishlistItem(id: Int): LiveData<FavouriteEntity?> {
        return wishlistDao.getOneWishlistItem(id)
    }

    override suspend fun deleteOneWishlist(wishlist: FavoriteEntity) {
        wishlistDao.deleteAWishlist(wishlist.toEntity())
    }

    override suspend fun deleteAllWishlist() {
        wishlistDao.deleteAllWishlist()
    }
}