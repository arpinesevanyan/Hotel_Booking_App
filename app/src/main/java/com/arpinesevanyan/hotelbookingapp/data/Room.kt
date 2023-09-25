package com.arpinesevanyan.hotelbookingapp.data

import android.os.Parcel
import android.os.Parcelable

data class Room(
    val rooms: List<RoomX>,
    val hotel: Hotel
)

data class RoomX(
    val name: String,
    val peculiarities: List<String>,
    val price: Int,
    val price_per: String,
    val image_urls: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeStringList(peculiarities)
        parcel.writeInt(price)
        parcel.writeString(price_per)
        parcel.writeStringList(image_urls)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomX> {
        override fun createFromParcel(parcel: Parcel): RoomX {
            return RoomX(parcel)
        }

        override fun newArray(size: Int): Array<RoomX?> {
            return arrayOfNulls(size)
        }
    }
}
