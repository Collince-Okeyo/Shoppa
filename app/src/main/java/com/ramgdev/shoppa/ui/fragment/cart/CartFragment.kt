package com.ramgdev.shoppa.ui.fragment.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.adapter.CartAdapter
import com.ramgdev.shoppa.databinding.FragmentCartBinding
import com.ramgdev.shoppa.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        cartAdapter = CartAdapter(CartAdapter.OnClickListener { product ->
            viewModel.deleteOneItemFromCart(product)
        })

        viewModel.cartItems.observe(viewLifecycleOwner) { cartItem ->
            cartAdapter.submitList(cartItem)
            val totalItems = "${cartItem.size.toString()}   Items"
            binding.recyclerViewCart.adapter = cartAdapter
            binding.tvItemsCount.text = totalItems
        }

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_paymentFragment2)
        }
        return binding.root
    }
}