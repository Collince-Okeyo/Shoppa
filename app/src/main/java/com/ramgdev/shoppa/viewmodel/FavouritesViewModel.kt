package com.ramgdev.shoppa.viewmodel

import androidx.lifecycle.ViewModel
import com.ramgdev.shoppa.data.local.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: FavouritesRepository): ViewModel() {


}