package com.example.furnitureapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.furnitureapp.databinding.ViewpagerImageItemBinding

class ViewPager2Images : RecyclerView.Adapter<ViewPager2Images.ViewPager2ImagesViewHolder>() {

    inner class ViewPager2ImagesViewHolder(
        private val binding: ViewpagerImageItemBinding) : ViewHolder(binding.root) {
            fun bind(imagePath: String) {

            }
        }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2ImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewpagerImageItemBinding.inflate(inflater, parent, false)
        return ViewPager2ImagesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewPager2ImagesViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }


}