package com.ramgdev.shoppa.ui.fragment.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ramgdev.shoppa.adapter.FavoritesAdapter
import com.ramgdev.shoppa.databinding.FragmentFavoriteBinding
import com.ramgdev.shoppa.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var adapter: FavoritesAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        adapter = FavoritesAdapter(FavoritesAdapter.OnClickListener { product ->
            viewModel.deleteOneItemFromFav(product)
        })

        viewModel.favItems.observe(viewLifecycleOwner) { favItem ->
            adapter.submitList(favItem)
            binding.recyclerViewFav.adapter = adapter
        }

        return binding.root
    }
}