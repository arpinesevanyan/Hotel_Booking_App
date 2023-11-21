package com.arpinesevanyan.hotelbookingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpinesevanyan.hotelbookingapp.model.network.Result
import com.arpinesevanyan.hotelbookingapp.model.data.HotelData
import com.arpinesevanyan.hotelbookingapp.model.repo.HotelRepository
import kotlinx.coroutines.launch

class HotelViewModel(private val repository: HotelRepository) : ViewModel() {
    private val _hotelDetails = MutableLiveData<Result<HotelData>>()
    val hotelDetails: LiveData<Result<HotelData>> = _hotelDetails

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




