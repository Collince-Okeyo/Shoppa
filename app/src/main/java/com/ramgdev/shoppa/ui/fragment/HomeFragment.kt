package com.ramgdev.shoppa.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ramgdev.shoppa.adapter.ProductsAdapter
import com.ramgdev.shoppa.databinding.FragmentHomeBinding
import com.ramgdev.shoppa.util.Resource
import com.ramgdev.shoppa.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        productsAdapter = ProductsAdapter(ProductsAdapter.OnClickListener{ products ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(products)
            findNavController().navigate(action)
        })

        subscribeToPostsObserver()

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

}