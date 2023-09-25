package com.arpinesevanyan.hotelbookingapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io/v3/35e0d18e-2521-4f1b-a575-f0fe366f66e3/"
    private const val BASE_URL1 = "https://run.mocky.io/v3/f9a38183-6f95-43aa-853a-9c83cbb05ecd/"
    private const val BASE_URL2 = "https://run.mocky.io/v3/e8868481-743f-4eb2-a0d7-2bc4012275c8/"

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

    fun createBookingApiService() : BookingApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BookingApiService::class.java)
    }
}