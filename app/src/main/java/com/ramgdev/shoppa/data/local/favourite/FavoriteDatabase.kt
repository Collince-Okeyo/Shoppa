package com.ramgdev.shoppa.data.local.favourite

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavouriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}