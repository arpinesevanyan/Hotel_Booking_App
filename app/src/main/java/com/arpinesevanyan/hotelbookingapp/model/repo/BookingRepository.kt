package com.arpinesevanyan.hotelbookingapp.model.repo

import com.arpinesevanyan.hotelbookingapp.model.network.BookingApiService
import com.arpinesevanyan.hotelbookingapp.model.network.Result
import com.arpinesevanyan.hotelbookingapp.model.data.BookingData

class BookingRepository(private val apiService: BookingApiService) {
    suspend fun getBookingData(id: Int): Result<BookingData> {
        return try {
            val response = apiService.getBooking(id)
            if (response.isSuccessful) {
                val bookingData = response.body()
                if (bookingData != null) {
                    Result.Success(bookingData)
                } else {
                    Result.Error("Response body is null")
                }
            } else {
                Result.Error("Request failed with ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("An error occurred: ${e.message}")
        }
    }
}


