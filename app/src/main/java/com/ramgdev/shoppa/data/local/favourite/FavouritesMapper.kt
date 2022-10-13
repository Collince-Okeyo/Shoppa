package com.ramgdev.shoppa.data.local.favourite

import com.ramgdev.shoppa.data.remote.model.Products
import com.ramgdev.shoppa.data.remote.model.favorite.Favorite

internal fun FavouriteEntity.toDomain(): Favorite {
    return Favorite(
        image = image,
        price = price,
        title = title,
        id = id,
        description = description,
        category = category,
        liked = liked
    )
}

internal fun Favorite.toEntity(): FavouriteEntity {
    return FavouriteEntity(
        image = image,
        price = price,
        title = title,
        id = id,
        description = description,
        liked = liked,
        category = category
    )
}

internal fun Favorite.toProduct(): Products {
    return Products(
        image = image,
        price = price,
        title = title,
        id = id,
        description = description,
        category = category
    )
}

internal fun Products.toProduct(): Favorite {
    return Favorite(
        image = image,
        price = price,
        title = title,
        id = id,
        category = category,
        description = description,
    )
}