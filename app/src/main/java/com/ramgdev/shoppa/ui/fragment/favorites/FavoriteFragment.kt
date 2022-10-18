package com.ramgdev.shoppa.ui.fragment.favorites

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        binding.imageViewDeleteAll.setOnClickListener {
            clearFav()
        }
        return binding.root
    }

    private fun clearFav() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to delete all favorites?")
        builder.setCancelable(true)
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
            Toast.makeText(requireContext(), "Delete cancelled", Toast.LENGTH_SHORT).show()
        }
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllFav()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}