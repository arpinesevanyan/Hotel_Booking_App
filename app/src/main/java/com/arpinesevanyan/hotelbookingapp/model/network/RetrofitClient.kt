package com.arpinesevanyan.hotelbookingapp.model.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io/v3/d144777c-a67f-4e35-867a-cacc3b827473/"
    private const val BASE_URL1 = "https://run.mocky.io/v3/8b532701-709e-4194-a41c-1a903af00195/"
    private const val BASE_URL2 = "https://run.mocky.io/v3/63866c74-d593-432c-af8e-f279d1a8d2ff/"

    fun create(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun createHotelApiService(): HotelApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(HotelApiService::class.java)
    }

    fun createBookingApiService(context: Context): BookingApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BookingApiService::class.java)
    }
}