package com.arpinesevanyan.hotelbookingapp.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object ImageBindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrlList")
    fun loadImageUrlList(imageView: ImageView, imageUrlList: List<String>?) {
        if (!imageUrlList.isNullOrEmpty()) {
            val imageUrl = imageUrlList[0]
            Picasso.get().load(imageUrl).into(imageView)
        }
    }
}
