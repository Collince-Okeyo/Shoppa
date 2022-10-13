package com.ramgdev.shoppa.viewmodel

import UiEvents
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramgdev.shoppa.data.local.repository.FavouritesRepository
import com.ramgdev.shoppa.data.remote.model.favorite.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: FavouritesRepository): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    val wishlistItems = repository.getWishlist()/*.value?.map { it.toDomain() }*/

    fun insertFavorite(wishlist: Favorite) {
        viewModelScope.launch {
            repository.insertToWishlist(wishlist)
        }
    }

    fun inWishlist(id: Int): LiveData<Boolean> {
        return repository.inWishlist(id)
    }

    fun deleteFromWishlist(wishlist: Favorite) {
        viewModelScope.launch {
            repository.deleteOneWishlist(wishlist)
            _eventFlow.emit(
                UiEvents.SnackbarEvent(message = "Item removed from wishlist")
            )
        }
    }

    fun deleteAllWishlist() {
        viewModelScope.launch {
            if (wishlistItems.value.isNullOrEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "No Wishlist items found")
                )
            } else {
                repository.deleteAllWishlist()
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "All items deleted from your wishlist")
                )
            }
        }
    }

}