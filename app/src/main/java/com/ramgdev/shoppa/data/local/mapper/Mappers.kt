package com.ramgdev.shoppa.data.local.mapper

import com.ramgdev.shoppa.data.local.entity.CartEntity
import com.ramgdev.shoppa.data.local.entity.FavoritesEntity
import com.ramgdev.shoppa.data.remote.model.Products

fun Products.toCartEntity(): CartEntity {
    return CartEntity(
        image = image,
        price = price,
        title = title,
        category = category,
        description = description
    )
}

fun Products.toFavEntity(): FavoritesEntity {
    return FavoritesEntity(
        image = image,
        price = price,
        title = title,
        category = category,
        description = description
    )
}