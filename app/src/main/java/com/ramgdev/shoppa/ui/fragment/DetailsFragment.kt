package com.ramgdev.shoppa.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs<DetailsFragmentArgs>()
    private lateinit var binding: FragmentDetailsBinding

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

        return binding.root
    }
}