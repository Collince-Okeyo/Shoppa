package com.ramgdev.shoppa.data.local.favourite

import com.ramgdev.shoppa.data.remote.model.favorite.FavoriteEntity

internal fun FavoriteEntity.toEntity(): FavouriteEntity {
    return FavouriteEntity(
        image = image,
        price = price,
        title = title,
        id = id,
        category = category,
        description = description
    )
}

