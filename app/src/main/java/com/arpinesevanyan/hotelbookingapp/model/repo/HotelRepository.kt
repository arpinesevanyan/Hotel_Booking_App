package com.arpinesevanyan.hotelbookingapp.model.repo

import com.arpinesevanyan.hotelbookingapp.model.network.ApiService
import com.arpinesevanyan.hotelbookingapp.model.network.Result
import com.arpinesevanyan.hotelbookingapp.model.data.HotelData

class HotelRepository(private val apiService: ApiService)  {
    suspend fun getHotelDetails(hotelId: Int): Result<HotelData> {
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

