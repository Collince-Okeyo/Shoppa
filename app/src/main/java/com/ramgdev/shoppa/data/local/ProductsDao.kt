package com.ramgdev.shoppa.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramgdev.shoppa.data.remote.model.Products

@Dao
interface ProductsDao {
    // If a new data is inserted with same primary key
    // It will get replaced by the previous one. This ensures that there is always a latest data in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(posts: List<Products>)

    // Query to fetch all the data from the SQLite database
    // No need of suspend method here
    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Products>>

    // Once the device comes online, the cached data need to be replaced, i.e. delete it
    // Again it will use coroutine to achieve this task
    @Query("DELETE FROM products")
    suspend fun deleteProducts()
}