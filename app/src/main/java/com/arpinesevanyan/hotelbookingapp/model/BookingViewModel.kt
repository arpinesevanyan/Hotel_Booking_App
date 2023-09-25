package com.arpinesevanyan.hotelbookingapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpinesevanyan.hotelbookingapp.api.Result
import com.arpinesevanyan.hotelbookingapp.data.BookingData
import com.arpinesevanyan.hotelbookingapp.repo.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel(private val bookingRepository: BookingRepository) : ViewModel() {
    private val _bookingData = MutableLiveData<Result<BookingData>>()
    val bookingData: LiveData<Result<BookingData>> = _bookingData

    fun getBookingData(bookingId: Int) {
        viewModelScope.launch {
            try {
                val result = bookingRepository.getBookingData(bookingId)
                if (result is Result.Success) {
                    _bookingData.postValue(result)
                } else {
                    _bookingData.postValue(Result.Error("Failed to fetch hotel data"))
                }
            } catch (e: Exception) {
                _bookingData.postValue(Result.Error(e.message ?: "An error occurred"))
            }
        }
    }
}
