package com.ramgdev.shoppa.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramgdev.shoppa.data.local.dao.ProductsDao
import com.ramgdev.shoppa.data.local.dao.CartDao
import com.ramgdev.shoppa.data.local.dao.FavoritesDao
import com.ramgdev.shoppa.data.local.entity.CartEntity
import com.ramgdev.shoppa.data.local.entity.FavoritesEntity
import com.ramgdev.shoppa.data.remote.model.Products

@Database(entities = [Products::class, CartEntity::class, FavoritesEntity::class], version = 2, exportSchema = false)
abstract class ProductsDatabase: RoomDatabase() {

    abstract fun getProductsDao(): ProductsDao
    abstract fun cartDao(): CartDao
    abstract fun favoritesDao(): FavoritesDao
}