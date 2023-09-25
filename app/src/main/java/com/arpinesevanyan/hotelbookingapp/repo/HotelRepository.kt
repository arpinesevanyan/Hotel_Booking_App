package com.arpinesevanyan.hotelbookingapp.repo

import com.arpinesevanyan.hotelbookingapp.api.ApiService
import com.arpinesevanyan.hotelbookingapp.api.Result
import com.arpinesevanyan.hotelbookingapp.data.Hotel

class HotelRepository(private val apiService: ApiService)  {
     suspend fun getHotelDetails(hotelId: Int): Result<Hotel> {
        return try {
            val response = apiService.getHotelDetails(hotelId)
            if (response.isSuccessful) {
                val hotel = response.body()
                if (hotel != null) {
                    Result.Success(hotel)
                } else {
                    Result.Error("Hotel data is null")
                }
            } else {
                Result.Error("Failed to fetch hotel data")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }
}

