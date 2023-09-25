package com.arpinesevanyan.hotelbookingapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpinesevanyan.hotelbookingapp.api.Result
import com.arpinesevanyan.hotelbookingapp.data.Hotel
import com.arpinesevanyan.hotelbookingapp.repo.HotelRepository
import kotlinx.coroutines.launch

class HotelViewModel(private val repository: HotelRepository) : ViewModel() {
    private val _hotelDetails = MutableLiveData<Result<Hotel>>()
    val hotelDetails: LiveData<Result<Hotel>> = _hotelDetails

    fun loadHotelDetails(id: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getHotelDetails(id)

                if (result is Result.Success) {
                    _hotelDetails.postValue(result)
                } else {
                    _hotelDetails.postValue(Result.Error("Failed to fetch hotel data"))
                }
            } catch (e: Exception) {
                _hotelDetails.postValue(Result.Error(e.message ?: "An error occurred"))
            }
        }
    }
}


