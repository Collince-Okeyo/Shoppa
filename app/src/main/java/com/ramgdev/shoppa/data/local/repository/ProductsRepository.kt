package com.ramgdev.shoppa.data.local.repository

import androidx.lifecycle.asFlow
import androidx.room.withTransaction
import com.ramgdev.shoppa.data.local.ProductsDatabase
import com.ramgdev.shoppa.data.remote.ProductsApiService
import com.ramgdev.shoppa.util.networkBoundResource
import kotlinx.coroutines.delay
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
            delay(1000)
            productsApiService.getProducts()
        },
        saveFetchResult = { products ->
            productsDatabase.withTransaction {
                productsDao.deleteProducts()
                productsDao.insertProducts(products)
            }
        }
    )
}