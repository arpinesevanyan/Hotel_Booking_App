package com.arpinesevanyan.hotelbookingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arpinesevanyan.hotelbookingapp.model.repo.HotelRepository

class HotelViewModelFactory(private val repository: HotelRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HotelViewModel::class.java)) {
            return HotelViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

