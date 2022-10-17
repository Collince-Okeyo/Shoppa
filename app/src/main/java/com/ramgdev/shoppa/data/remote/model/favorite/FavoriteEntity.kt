package com.ramgdev.shoppa.data.remote.model.favorite

import androidx.room.Entity

@Entity(tableName = "favorites_cart")
class FavoriteEntity(
    val image: String,
    val price: Double,
    val title: String,
    val description: String,
    val category: String,
    val id: Int,
)
