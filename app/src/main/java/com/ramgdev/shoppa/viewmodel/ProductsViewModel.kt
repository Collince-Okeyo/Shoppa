package com.ramgdev.shoppa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ramgdev.shoppa.data.local.repository.product.ProductsRepository
import com.ramgdev.shoppa.data.remote.model.Products
import com.ramgdev.shoppa.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsRepository: ProductsRepository): ViewModel() {

    var products: LiveData<Resource<List<Products>>>? = null
    // Called the moment ViewModel is created
    init {
        getPost()
    }

    private fun getPost(){
        viewModelScope.launch {
            products = productsRepository.getProducts().asLiveData()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Products>> {
        return productsRepository.searchDatabase(searchQuery).asLiveData()
    }

}