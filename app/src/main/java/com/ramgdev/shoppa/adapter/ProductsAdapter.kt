package com.ramgdev.shoppa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.data.remote.model.Products
import com.ramgdev.shoppa.databinding.HomeRowBinding
import com.ramgdev.shoppa.ui.fragment.home.HomeFragmentDirections

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
        val cart = binding.imageViewCart
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
        holder.bind(product)

        val cartIcon = holder.itemView.findViewById<CheckBox>(R.id.imageViewCart)
        cartIcon.setOnClickListener {
            val isChecked = cartIcon.isChecked
            if (isChecked) {
                onClickListener.onClick(product)
                Toast.makeText(holder.itemView.context, "Added to cart", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(holder.itemView.context, "Already added to cart", Toast.LENGTH_SHORT).show()
            }
        }

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToItemDetailsFragment(product)
            Navigation.findNavController(it).navigate(action)
        }
    }

    class OnClickListener(val clickListener: (products: Products) -> Unit) {
        fun onClick(products: Products) = clickListener(products)
    }

}