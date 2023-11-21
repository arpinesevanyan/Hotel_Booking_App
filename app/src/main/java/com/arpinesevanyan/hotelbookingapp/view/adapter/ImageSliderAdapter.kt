package com.arpinesevanyan.hotelbookingapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arpinesevanyan.hotelbookingapp.R
import com.arpinesevanyan.hotelbookingapp.databinding.ItemImageSliderBinding
import com.bumptech.glide.Glide

class ImageSliderAdapter(private val imageUrls: List<String>?) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val binding = ItemImageSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val imageUrl = imageUrls?.get(position)
        imageUrl?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return imageUrls?.size ?: 0
    }

    inner class ImageSliderViewHolder(private val binding: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(binding.root)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageView)
        }
    }
}
