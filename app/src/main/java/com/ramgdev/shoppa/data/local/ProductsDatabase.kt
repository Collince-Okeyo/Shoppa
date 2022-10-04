package com.ramgdev.shoppa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramgdev.shoppa.data.remote.model.Products

@Database(entities = [Products::class], version = 1)
abstract class ProductsDatabase: RoomDatabase() {

    abstract fun getProductsDao(): ProductsDao

}