package com.arpinesevanyan.hotelbookingapp.model.network

import com.arpinesevanyan.hotelbookingapp.model.data.BookingData
import com.arpinesevanyan.hotelbookingapp.model.data.HotelData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("hotels/{id}")
    suspend fun getHotelDetails(@Path("id") hotelId: Int): Response<HotelData>
}

interface HotelApiService {
    @GET("rooms")
    fun getRooms(): Call<RoomResponse>
}

interface BookingApiService {
    @GET("bookings/{id}")
    suspend fun getBooking(@Path("id") bookingId: Int): Response<BookingData>
}


