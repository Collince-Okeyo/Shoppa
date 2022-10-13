package com.ramgdev.shoppa.data.local.favourite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesDao {
    @Insert
    suspend fun insertToWishlist(wishlistEntity: FavouriteEntity)

    @Query("SELECT * FROM wishlist_table ORDER BY id DESC")
    fun getWishlist(): LiveData<List<FavouriteEntity>>

    @Query("SELECT * FROM wishlist_table WHERE id  == :id")
    fun getOneWishlistItem(id: Int): LiveData<FavouriteEntity?>

    @Query("SELECT liked FROM wishlist_table WHERE id = :id")
    fun inWishlist(id: Int): LiveData<Boolean>

    @Delete
    suspend fun deleteAWishlist(wishlistEntity: FavouriteEntity)

    @Query("DELETE FROM wishlist_table")
    suspend fun deleteAllWishlist()
}