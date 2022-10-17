package com.ramgdev.shoppa.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ramgdev.shoppa.data.local.entity.CartEntity

@Dao
interface CartDao {
    @Insert
    suspend fun insertToWishlist(wishlistEntity: CartEntity)

    @Query("SELECT * FROM cart_items_table ORDER BY id DESC")
    fun getCartItems(): LiveData<List<CartEntity>>

    @Query("SELECT * FROM cart_items_table WHERE id  == :id")
    fun getOneCartItemItem(id: Int): LiveData<CartEntity?>

    @Query("SELECT liked FROM cart_items_table WHERE id = :id")
    fun inCartList(id: Int): LiveData<Boolean>

    @Delete
    suspend fun deleteOneCartItem(cartEntity: CartEntity)

    @Query("DELETE FROM cart_items_table")
    suspend fun deleteAllCartItems()
}