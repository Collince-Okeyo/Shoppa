package com.ramgdev.shoppa.data.local.repository.product

import androidx.lifecycle.asFlow
import androidx.room.withTransaction
import com.ramgdev.shoppa.data.local.db.ProductsDatabase
import com.ramgdev.shoppa.data.remote.ProductsApiService
import com.ramgdev.shoppa.data.remote.model.Products
import com.ramgdev.shoppa.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsApiService: ProductsApiService,
    private val productsDatabase: ProductsDatabase
) {

    private val productsDao = productsDatabase.getProductsDao()

    suspend fun getProducts() = networkBoundResource(
        query = {
            productsDao.getAllProducts().asFlow()
        },
        fetch = {
            delay(100)
            productsApiService.getProducts()
        },
        saveFetchResult = { products ->
            productsDatabase.withTransaction {
                productsDao.deleteProducts()
                productsDao.insertProducts(products)
            }
        }
    )

    fun searchDatabase(searchQuery: String): Flow<List<Products>> {
        return productsDao.searchDatabase(searchQuery)
    }
}