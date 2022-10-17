package com.ramgdev.shoppa.ui.fragment.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ramgdev.shoppa.adapter.CartAdapter
import com.ramgdev.shoppa.databinding.FragmentCartBinding
import com.ramgdev.shoppa.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var adapter: CartAdapter
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        adapter = CartAdapter(CartAdapter.OnClickListener { product ->
            viewModel.deleteOneItemFromCart(product)
        })

        viewModel.cartItems.observe(viewLifecycleOwner) { cartItem ->
            adapter.submitList(cartItem)
            binding.recyclerViewCart.adapter = adapter
        }

        return binding.root
    }
}