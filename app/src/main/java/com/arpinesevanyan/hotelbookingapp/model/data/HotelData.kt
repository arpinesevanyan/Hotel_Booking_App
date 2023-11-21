package com.arpinesevanyan.hotelbookingapp.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HotelData(
    @SerializedName("about_the_hotel") val about_the_hotel: AboutTheHotel? = null,
    @SerializedName("adress") val adress: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("image_urls") val image_urls: List<String>? = null,
    @SerializedName("minimal_price") val minimal_price: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("price_for_it") val price_for_it: String? = null,
    @SerializedName("rating") val rating: Int? = null,
    @SerializedName("rating_name") val rating_name: String? = null,
) : Serializable

data class AboutTheHotel(
    @SerializedName("description") val description: String? = null,
    @SerializedName("peculiarities") val peculiarities: List<String>? = null,
) : Serializable