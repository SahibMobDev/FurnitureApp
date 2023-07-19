package com.example.furnitureapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furnitureapp.data.Product
import com.example.furnitureapp.databinding.SpecialRvItemBinding
import com.example.furnitureapp.util.Resource

class SpecialProductsAdapter : RecyclerView.Adapter<SpecialProductsAdapter.SpecialProductViewHolder>() {

    inner class SpecialProductViewHolder(
        private val binding: SpecialRvItemBinding) : RecyclerView.ViewHolder(binding.root
    ) {
            fun bin(product: Product) {
                binding.apply {
                    Glide.with(itemView).load(product.images[0]).into(imageSpecialRvItem)
                    tvSpecialProductName.text = product.name
                    tvSpecialProductPrice.text = product.price.toString()
                }
            }
        }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SpecialRvItemBinding.inflate(inflater, parent, false)
        return SpecialProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SpecialProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bin(product)
    }


}