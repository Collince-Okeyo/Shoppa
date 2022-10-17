package com.ramgdev.shoppa.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ramgdev.shoppa.data.local.entity.CartEntity
import com.ramgdev.shoppa.data.local.entity.FavoritesEntity
import com.ramgdev.shoppa.data.remote.model.Products

@Dao
interface FavoritesDao {
    @Insert
    suspend fun insertToFavorites(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorites_table ORDER BY id DESC")
    fun getFavoriteItems(): LiveData<List<FavoritesEntity>>

    @Query("SELECT liked FROM favorites_table WHERE id = :id")
    fun inFavoritesList(id: Int): Boolean

    @Delete
    suspend fun deleteOneFavoriteItem(products: FavoritesEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavoriteItems()
}