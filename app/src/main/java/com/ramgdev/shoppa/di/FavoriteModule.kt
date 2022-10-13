package com.ramgdev.shoppa.di

import android.content.Context
import androidx.room.Room
import com.ramgdev.shoppa.data.local.favourite.FavoriteDatabase
import com.ramgdev.shoppa.data.local.repository.FavoritesRepositoryImp
import com.ramgdev.shoppa.data.local.repository.FavouritesRepository
import com.ramgdev.shoppa.util.Constants.WISHLIST_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    /*@Provides
    @Singleton
    fun provideWishlistDatabase(application: Application): FavoriteDatabase =
        Room.databaseBuilder(application, FavoriteDatabase::class.java, WISHLIST_DATABASE).build()*/

    @Provides
    @Singleton
    fun provideWishlistDatabase(
        @ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoriteDatabase::class.java,
            WISHLIST_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWishlistRepository(wishlistDatabase: FavoriteDatabase): FavouritesRepository {
        return FavoritesRepositoryImp(wishlistDatabase.favoritesDao())
    }
}