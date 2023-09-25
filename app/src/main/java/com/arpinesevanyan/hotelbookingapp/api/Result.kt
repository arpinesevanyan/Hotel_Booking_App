package com.arpinesevanyan.hotelbookingapp.api

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
}

