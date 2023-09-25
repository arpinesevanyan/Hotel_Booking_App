package com.arpinesevanyan.hotelbookingapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arpinesevanyan.hotelbookingapp.repo.BookingRepository

class BookingViewModelFactory(private val bookingRepository: BookingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookingViewModel::class.java)) {
            return BookingViewModel(bookingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}