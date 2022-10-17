package com.ramgdev.shoppa.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.adapter.ProductsAdapter
import com.ramgdev.shoppa.data.remote.model.favorite.FavoriteEntity
import com.ramgdev.shoppa.databinding.FragmentDetailsBinding
import com.ramgdev.shoppa.viewmodel.FavouritesViewModel

private const val CLICKED_KEY = "clicked_key"
class DetailsFragment : Fragment() {

    private var clicked = false
    private val args: DetailsFragmentArgs by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var productsAdapter: ProductsAdapter
    val viewModel: FavouritesViewModel by viewModels()
//    private lateinit var viewModel: FavouritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val name = args.productsDetails.title
        val desc = args.productsDetails.description
        val image = args.productsDetails.image
        val price = "${args.productsDetails.price.toString()}$"

        binding.tvProductName.text = name
        binding.tvProductDesc.text = desc
        binding.tvPrice.text = price
        Glide.with(binding.productImage)
            .load(image)
            .into(binding.productImage)

        binding.imageViewBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)
        }

        binding.imageFav.setOnClickListener {

            if (savedInstanceState != null) {
                binding.imageView3.isVisible = true
            }

        }

        binding.buttonOrder.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_paymentFragment2)
        }
        return binding.root
    }
}