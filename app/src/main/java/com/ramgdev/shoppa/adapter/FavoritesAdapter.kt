package com.ramgdev.shoppa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdev.shoppa.data.local.entity.FavoritesEntity
import com.ramgdev.shoppa.databinding.FavoriteRowBinding

class FavoritesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<FavoritesEntity, FavoritesAdapter.CartViewHolder>(CartDiffUtil) {

    object CartDiffUtil : DiffUtil.ItemCallback<FavoritesEntity>() {
        override fun areItemsTheSame(oldItem: FavoritesEntity, newItem: FavoritesEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FavoritesEntity,
            newItem: FavoritesEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class CartViewHolder(private val binding: FavoriteRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val deleteFromCart = binding.imageViewFavDel

        @SuppressLint("SetTextI18n")
        fun bind(cartItem: FavoritesEntity?) {

            Glide.with(binding.imageViewFav)
                .load(cartItem?.image)
                .into(binding.imageViewFav)

            binding.textViewFavName.text = cartItem?.title
            binding.textView3Cost.text = "${cartItem?.price.toString()}$"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            FavoriteRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = getItem(position)

        holder.deleteFromCart.setOnClickListener {
            onClickListener.onClick(cartItem)
        }
        holder.bind(cartItem)
    }

    class OnClickListener(val clickListener: (cart: FavoritesEntity) -> Unit) {
        fun onClick(cart: FavoritesEntity) = clickListener(cart)
    }
}