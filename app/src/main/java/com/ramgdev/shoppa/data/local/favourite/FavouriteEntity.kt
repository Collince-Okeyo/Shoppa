package com.ramgdev.shoppa.data.local.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ramgdev.shoppa.util.Constants.WISHLIST_TABLE_NAME

@Entity(tableName = WISHLIST_TABLE_NAME)
data class FavouriteEntity(
    val image: String,
    val price: Double,
    val title: String,
    val category: String,
    val description: String,
    val liked: Boolean = false,
    @PrimaryKey val id: Int,
)
