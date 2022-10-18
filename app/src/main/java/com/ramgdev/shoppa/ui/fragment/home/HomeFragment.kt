package com.ramgdev.shoppa.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mancj.materialsearchbar.MaterialSearchBar.OnSearchActionListener
import com.ramgdev.shoppa.adapter.ProductsAdapter
import com.ramgdev.shoppa.databinding.FragmentHomeBinding
import com.ramgdev.shoppa.util.Resource
import com.ramgdev.shoppa.viewmodel.CartViewModel
import com.ramgdev.shoppa.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@Suppress("IMPLICIT_CAST_TO_ANY")
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ProductsViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        productsAdapter = ProductsAdapter(ProductsAdapter.OnClickListener{ product ->
            cartViewModel.addToCart(product)
        })

        subscribeToPostsObserver()

        binding.searchBar.setOnSearchActionListener(object : OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {
                val s = if (enabled) "enabled" else subscribeToPostsObserver()
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                searchProducts(text.toString())
            }

            override fun onButtonClicked(buttonCode: Int) {}
        })

        return binding.root
    }

    private fun subscribeToPostsObserver(){
        viewModel.products?.observe(viewLifecycleOwner, Observer { result ->
            when (result){
                is Resource.Loading ->{
                    binding.progressBar2.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar2.isVisible = false
                    val posts  = result.data
                    Timber.d("PRODUCTS::  ${posts.toString()}")
                    productsAdapter.submitList(posts)
                    binding.homeRecyclerView.adapter = productsAdapter
                }
                is Resource.Error -> {
                    binding.progressBar2.isVisible = false
                    Snackbar.make(binding.root, result.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                else -> {
                    Timber.d("Exception")
                }
            }
        })
    }

    private fun searchProducts(query: String) {
        // %" "% because our custom sql query will require that
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                productsAdapter.submitList(it)
            }
        }
    }

}