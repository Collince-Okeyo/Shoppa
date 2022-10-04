package com.ramgdev.shoppa.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.data.remote.model.Products
import com.ramgdev.shoppa.databinding.HomeRowBinding
import timber.log.Timber

class ProductsAdapter(private val onClickListener: OnClickListener): ListAdapter<Products, ProductsAdapter.ProductsViewHolder>(ProductsDiffUtil) {

    object ProductsDiffUtil: DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class ProductsViewHolder(private val binding: HomeRowBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Products?) {

            Glide.with(binding.imageViewItem)
                .load(product?.image)
                .into(binding.imageViewItem)

            binding.textViewName.text = product?.title
            binding.textViewCategory.text = product?.category
            binding.textViewPrice.text = "${product?.price.toString()}$"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(HomeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }

    class OnClickListener(val clickListener: (products: Products) -> Unit) {
        fun onClick(products: Products) = clickListener(products)
    }
}