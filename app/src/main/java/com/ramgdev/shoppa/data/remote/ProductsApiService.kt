package com.ramgdev.shoppa.data.remote

import com.ramgdev.shoppa.data.remote.model.Products
import retrofit2.http.GET

interface ProductsApiService {
    @GET("products")
    suspend fun getProducts(): List<Products>

    /*@GET("products/categories")
    suspend fun getProductCategories(): List<String>*/
}