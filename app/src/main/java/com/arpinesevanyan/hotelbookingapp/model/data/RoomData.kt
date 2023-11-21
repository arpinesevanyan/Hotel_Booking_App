package com.arpinesevanyan.hotelbookingapp.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RoomData(
    @SerializedName("rooms") val rooms: List<RoomX> = emptyList(),
) : Serializable

data class RoomX(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("image_urls") val image_urls: List<String> = emptyList(),
    @SerializedName("name") val name: String = "",
    @SerializedName("peculiarities") val peculiarities: List<String> = emptyList(),
    @SerializedName("price") val price: Int = 0,
    @SerializedName("price_per") val price_per: String = "",
): Serializable
