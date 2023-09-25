package com.arpinesevanyan.hotelbookingapp.ui

import android.content.Intent
import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.arpinesevanyan.hotelbookingapp.R
import com.arpinesevanyan.hotelbookingapp.api.RetrofitClient
import com.arpinesevanyan.hotelbookingapp.api.Result
import com.arpinesevanyan.hotelbookingapp.data.BookingData
import com.arpinesevanyan.hotelbookingapp.databinding.ActivityBookingBinding
import com.arpinesevanyan.hotelbookingapp.model.BookingViewModel
import com.arpinesevanyan.hotelbookingapp.model.BookingViewModelFactory
import com.arpinesevanyan.hotelbookingapp.repo.BookingRepository

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var bookingViewModel: BookingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bookingId = intent.getIntExtra("bookingId", 0)

        val apiService = RetrofitClient.createBookingApiService()
        val bookingRepository = BookingRepository(apiService)

        val addTouristButton = findViewById<View>(R.id.addTouristButton)
        val payButton = findViewById<AppCompatButton>(R.id.payButton)

        addTouristButton.setOnClickListener {
            val fragment = TouristFormDialogFragment()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        bookingViewModel = ViewModelProvider(
            this,
            BookingViewModelFactory(bookingRepository)
        ).get(BookingViewModel::class.java)

        val bookingObserver = Observer<Result<BookingData>> { result ->
            when (result) {
                is Result.Success -> {
                    val bookingData = result.data

                    binding.hotelRating.rating = bookingData.horating / 2.toFloat()
                    binding.hotelName.text = bookingData.hotel_name
                    binding.hotelAddress.text = bookingData.hotel_adress
                    binding.departureCity.text = "Вылет из ${bookingData.departure}"
                    binding.arrivalCity.text = "Страна, город ${bookingData.arrival_country}"
                    binding.bookingDates.text =
                        "Даты ${bookingData.tour_date_start} - ${bookingData.tour_date_stop}"
                    binding.number.text = "Кол-во ночей ${bookingData.number_of_nights}"
                    binding.hotel.text = "Отель ${bookingData.hotel_name}"
                    binding.room.text = "Номер ${bookingData.room}"
                    binding.nutrition.text = "Питание ${bookingData.nutrition}"
                    binding.tourPriceTextView.text = "Тур ${bookingData.tour_price}"
                    binding.fuelCharge.text = "Топливный сбор ${bookingData.fuel_charge}"
                    binding.serviceCharge.text = "Сервисный сбор ${bookingData.service_charge}"

                    binding.totalPrice.text = "Итоговая цена ${bookingData.tour_price + bookingData.fuel_charge + bookingData.service_charge}"

                    val totalPrice = bookingData.tour_price + bookingData.fuel_charge + bookingData.service_charge
                    payButton.text = "Оплатить: $totalPrice"

                    payButton.setOnClickListener {
                        val intent = Intent(this, PaidActivity::class.java)
                        startActivity(intent)
                    }
                }
                is Result.Error -> {
                    val error = result.errorMessage
                    showErrorDialog(error)
                }
            }
        }
        bookingViewModel.bookingData.observe(this, bookingObserver)
        bookingViewModel.getBookingData(bookingId)

    }

    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle("Ошибка")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
