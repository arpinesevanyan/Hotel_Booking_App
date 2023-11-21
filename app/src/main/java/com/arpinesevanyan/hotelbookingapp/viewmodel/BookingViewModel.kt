package com.arpinesevanyan.hotelbookingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpinesevanyan.hotelbookingapp.model.network.Result
import com.arpinesevanyan.hotelbookingapp.model.data.BookingData
import com.arpinesevanyan.hotelbookingapp.model.repo.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel(private val bookingRepository: BookingRepository) : ViewModel() {
    private val _bookingData =  MutableLiveData<Result<BookingData>>()
    val bookingData: LiveData<Result<BookingData>> = _bookingData

    fun getBookingData(bookingId: Int) {
        viewModelScope.launch {
            try {
                val result = bookingRepository.getBookingData(bookingId)
                _bookingData.value = result
            } catch (e: Exception) {
                _bookingData.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }
}
