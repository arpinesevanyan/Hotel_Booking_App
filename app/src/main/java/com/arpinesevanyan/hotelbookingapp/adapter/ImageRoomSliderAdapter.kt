package com.arpinesevanyan.hotelbookingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arpinesevanyan.hotelbookingapp.R
import com.arpinesevanyan.hotelbookingapp.databinding.ItemImageRoomSliderBinding
import com.bumptech.glide.Glide

class ImageRoomSliderAdapter(private val imageUrls: List<String>) :
    RecyclerView.Adapter<ImageRoomSliderAdapter.ImageSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageRoomSliderBinding.inflate(inflater, parent, false)
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageSliderViewHolder(private val binding: ItemImageRoomSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            Glide.with(binding.root)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageRoom)
        }
    }
}

