package com.arpinesevanyan.hotelbookingapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arpinesevanyan.hotelbookingapp.adapter.ImageSliderAdapter
import com.arpinesevanyan.hotelbookingapp.api.Result
import com.arpinesevanyan.hotelbookingapp.api.RetrofitClient
import com.arpinesevanyan.hotelbookingapp.data.Hotel
import com.arpinesevanyan.hotelbookingapp.databinding.ActivityHotelDetailBinding
import com.arpinesevanyan.hotelbookingapp.model.HotelViewModel
import com.arpinesevanyan.hotelbookingapp.model.HotelViewModelFactory
import com.arpinesevanyan.hotelbookingapp.repo.HotelRepository

class HotelDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelDetailBinding
    private lateinit var viewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hotelId = intent.getIntExtra("hotel_id", 0)
        val apiService = RetrofitClient.create()
        val hotelRepository = HotelRepository(apiService)
        val viewModelFactory = HotelViewModelFactory(hotelRepository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HotelViewModel::class.java)

        val hotelDetailsObserver = Observer<Result<Hotel>> { result ->
            when (result) {
                is Result.Success -> {
                    val hotel = result.data

                    val imageUrls = hotel.image_urls
                    Log.d("Debug", "Image URLs: $imageUrls")

                    val imageSliderAdapter = ImageSliderAdapter(imageUrls)
                    binding.viewPager.adapter = imageSliderAdapter

                    binding.buttonChooseRoom.setOnClickListener {
                        val intent = Intent(this, RoomActivity::class.java)
                        intent.putExtra("hotel_id", hotelId)
                        startActivity(intent)
                    }

                    binding.textHotelName.text = hotel.name
                    binding.textHotelAddress.text = hotel.adress
                    binding.ratingBarHotel.rating = hotel.rating / 2.toFloat()
                    binding.textHotelPrice.text = hotel.price_for_it
                    binding.textHotelDescription.text = hotel.about_the_hotel.description

                    val peculiarities = hotel.about_the_hotel.peculiarities
                    val peculiaritiesText = peculiarities.joinToString(", ")
                    binding.textHotelPeculiarities.text = peculiaritiesText
                }
                is Result.Error -> {
                    val error = result.errorMessage
                    showErrorDialog(error)
                }
            }
        }

        viewModel.hotelDetails.observe(this, hotelDetailsObserver)

        viewModel.loadHotelDetails(hotelId)
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
