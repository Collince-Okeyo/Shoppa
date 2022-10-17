package com.ramgdev.shoppa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdev.shoppa.data.local.entity.CartEntity
import com.ramgdev.shoppa.databinding.CartRowBinding

class CartAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<CartEntity, CartAdapter.CartViewHolder>(CartDiffUtil) {

    object CartDiffUtil : DiffUtil.ItemCallback<CartEntity>() {
        override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class CartViewHolder(private val binding: CartRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val deleteFromCart = binding.imageViewDeleteFromCart

        @SuppressLint("SetTextI18n")
        fun bind(cartItem: CartEntity?) {

            Glide.with(binding.cartImageView)
                .load(cartItem?.image)
                .into(binding.cartImageView)

            binding.cartItemName.text = cartItem?.title
            binding.itemPieces.text = "${cartItem?.category} items"
            binding.cartItemPrice.text = "${cartItem?.price.toString()}$"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            CartRowBinding.inflate(
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

    class OnClickListener(val clickListener: (cart: CartEntity) -> Unit) {
        fun onClick(cart: CartEntity) = clickListener(cart)
    }
}