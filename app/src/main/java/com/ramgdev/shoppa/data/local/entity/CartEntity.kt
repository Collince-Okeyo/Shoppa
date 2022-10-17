package com.ramgdev.shoppa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ramgdev.shoppa.util.Constants.CART_ITEMS_TABLE_NAME

@Entity(tableName = CART_ITEMS_TABLE_NAME)
data class CartEntity(
    val image: String,
    val price: Double,
    val title: String,
    val category: String,
    val description: String,
    val liked: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
)
