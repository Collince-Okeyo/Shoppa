package com.ramgdev.shoppa.data.remote.model.favorite

data class Favorite(
    val image: String,
    val price: Double,
    val title: String,
    val description: String,
    val category: String,
    val id: Int,
    val liked: Boolean = false,
)
