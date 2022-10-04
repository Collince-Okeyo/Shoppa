package com.ramgdev.shoppa.di

import android.app.Application
import androidx.room.Room
import com.ramgdev.shoppa.data.local.ProductsDatabase
import com.ramgdev.shoppa.data.local.repository.ProductsRepository
import com.ramgdev.shoppa.data.remote.ProductsApiService
import com.ramgdev.shoppa.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide Api Service
    @Provides
    @Singleton
    fun provideProductsApiService(): ProductsApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsApiService::class.java)
    }

    // Provide repository
    @Provides
    @Singleton
    fun provideProductsRepository(
        productsApiService: ProductsApiService,
        productsDatabase: ProductsDatabase
    ): ProductsRepository {
        return ProductsRepository(productsApiService, productsDatabase)
    }

    // Provide DB
    @Provides
    @Singleton
    fun provideProductsDatabase(application: Application): ProductsDatabase =
        Room.databaseBuilder(application, ProductsDatabase::class.java, "fake_products").build()

}