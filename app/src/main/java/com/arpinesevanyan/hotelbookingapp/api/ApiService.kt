package com.arpinesevanyan.hotelbookingapp.api

import com.arpinesevanyan.hotelbookingapp.data.BookingData
import com.arpinesevanyan.hotelbookingapp.data.Hotel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("hotels/{id}")
    suspend fun getHotelDetails(@Path("id") hotelId: Int): Response<Hotel>
}

interface HotelApiService {
    @GET("rooms")
    fun getRooms(): Call<RoomResponse>
}

interface BookingApiService {
    @GET("bookings/{id}")
    suspend fun getBooking(@Path("id") bookingId: Int): Response<BookingData>
}


