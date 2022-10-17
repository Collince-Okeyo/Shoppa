package com.ramgdev.shoppa.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.data.remote.model.favorite.FavoriteEntity
import com.ramgdev.shoppa.databinding.FragmentFavoriteBinding
import com.ramgdev.shoppa.viewmodel.FavouritesViewModel

class FavoriteFragment : Fragment() {

    val viewModel: FavouritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)



        return binding.root
    }
}