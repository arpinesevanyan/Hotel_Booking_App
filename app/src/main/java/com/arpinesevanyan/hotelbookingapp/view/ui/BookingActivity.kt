package com.arpinesevanyan.hotelbookingapp.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.arpinesevanyan.hotelbookingapp.R
import com.arpinesevanyan.hotelbookingapp.model.network.RetrofitClient
import com.arpinesevanyan.hotelbookingapp.model.network.Result
import com.arpinesevanyan.hotelbookingapp.model.data.BookingData
import com.arpinesevanyan.hotelbookingapp.databinding.ActivityBookingBinding
import com.arpinesevanyan.hotelbookingapp.model.data.HotelData
import com.arpinesevanyan.hotelbookingapp.viewmodel.BookingViewModel
import com.arpinesevanyan.hotelbookingapp.viewmodel.BookingViewModelFactory
import com.arpinesevanyan.hotelbookingapp.model.repo.BookingRepository
import com.arpinesevanyan.hotelbookingapp.view.ui.fragment.TouristFormDialogFragment
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var bookingViewModel: BookingViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Бронирование"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val bookingId = intent.getIntExtra("bookingId", 0)
        val apiService = RetrofitClient.createBookingApiService(this)
        val bookingRepository = BookingRepository(apiService)
        val viewModelFactory = BookingViewModelFactory(bookingRepository)

        bookingViewModel =
            ViewModelProvider(this, viewModelFactory).get(BookingViewModel::class.java)

        val addTouristButton = findViewById<View>(R.id.addTouristButton)
        val payButton = findViewById<AppCompatButton>(R.id.payButton)

        addTouristButton.setOnClickListener {
            val fragment = TouristFormDialogFragment()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        val bookingObserver = Observer<Result<BookingData>> { result ->
            when (result) {
                is Result.Success -> {
                    val bookingData = result.data

                    binding.ratingBarHotel.rating =
                        bookingData.horating?.let { it / 2.toFloat() } ?: 0f
                    binding.textHotelName.text = bookingData.hotel_name
                    binding.textHotelAddress.text = bookingData.hotel_adress
                    binding.departureCity.text = "Вылет из ${bookingData.departure}"
                    binding.arrivalCity.text = "Страна, город ${bookingData.arrival_country}"
                    binding.bookingDates.text =
                        "Даты ${bookingData.tourDateStart} - ${bookingData.tourDateStop}"
                    binding.number.text = "Кол-во ночей ${bookingData.number_of_nights}"
                    binding.hotel.text = "Отель ${bookingData.hotel_name}"
                    binding.room.text = "Номер ${bookingData.room}"
                    binding.nutrition.text = "Питание ${bookingData.nutrition}"
                    binding.tourPriceTextView.text = "Тур ${bookingData.tour_price}"
                    binding.fuelCharge.text = "Топливный сбор ${bookingData.fuel_charge}"
                    binding.serviceCharge.text = "Сервисный сбор ${bookingData.service_charge}"

                    binding.totalPrice.text =
                        "Итоговая цена ${bookingData.tour_price!! + bookingData.fuel_charge!! + bookingData.service_charge!!}"

                    val totalPrice =
                        bookingData.tour_price!! + bookingData.fuel_charge + bookingData.service_charge!!
                    payButton.text = "Оплатить: $totalPrice"

                    payButton.setOnClickListener {
                        val intent = Intent(this, PaidActivity::class.java)
                        startActivity(intent)
                    }
                }

                is Result.Error -> {
                    val error = result.message
                    showErrorDialog(error)
                }

                Result.Loading -> {
                }
            }
        }

        bookingViewModel.bookingData.observe(this, bookingObserver)

        bookingViewModel.getBookingData(bookingId)
    }

    private fun showErrorDialog(errorMessage: String) {
        Log.e("BookingActivity", "Error: $errorMessage")  // Log the error message
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}


