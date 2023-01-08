package com.ramgdev.shoppa.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.data.local.mapper.toFavEntity
import com.ramgdev.shoppa.databinding.FragmentItemDetailsBinding
import com.ramgdev.shoppa.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ItemDetailsFragment : Fragment() {

    private val args: ItemDetailsFragmentArgs by navArgs<ItemDetailsFragmentArgs>()
    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var binding: FragmentItemDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
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
            findNavController().popBackStack()
        }

        binding.imageFav.setOnClickListener {

            if (viewModel.inFavorites(args.productsDetails)){
                viewModel.deleteOneItemFromFav(args.productsDetails.toFavEntity())
            }else{
                viewModel.addToFav(args.productsDetails)
            }

            if (savedInstanceState != null) {
                binding.imageView3.isVisible = true
            }

        }

        binding.buttonOrder.setOnClickListener {
            findNavController().navigate(R.id.action_itemDetailsFragment_to_paymentFragment2)
        }
        return binding.root
    }

    companion object {
        private const val CLICKED_KEY = "clicked_key"
    }
}