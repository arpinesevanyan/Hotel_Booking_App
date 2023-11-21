package com.arpinesevanyan.hotelbookingapp.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arpinesevanyan.hotelbookingapp.R
import com.arpinesevanyan.hotelbookingapp.view.adapter.ImageSliderAdapter
import com.arpinesevanyan.hotelbookingapp.model.network.Result
import com.arpinesevanyan.hotelbookingapp.model.network.RetrofitClient
import com.arpinesevanyan.hotelbookingapp.model.data.HotelData
import com.arpinesevanyan.hotelbookingapp.databinding.ActivityHotelDetailBinding
import com.arpinesevanyan.hotelbookingapp.viewmodel.HotelViewModel
import com.arpinesevanyan.hotelbookingapp.viewmodel.HotelViewModelFactory
import com.arpinesevanyan.hotelbookingapp.model.repo.HotelRepository
import com.arpinesevanyan.hotelbookingapp.view.ui.fragment.HotelBottomSheetFragment

class HotelDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelDetailBinding
    private lateinit var viewModel: HotelViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Отель"

        val hotelId = intent.getIntExtra("hotel_id", 0)
        val apiService = RetrofitClient.create()
        val hotelRepository = HotelRepository(apiService)
        val viewModelFactory = HotelViewModelFactory(hotelRepository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HotelViewModel::class.java)

        val hotelDetailsObserver = Observer<Result<HotelData>> { result ->
            when (result) {
                is Result.Success -> {
                    val hotel = result.data

                    val imageUrls = hotel.image_urls

                    val imageSliderAdapter = imageUrls?.let { ImageSliderAdapter(it) }
                    binding.viewPager.adapter = imageSliderAdapter

                    binding.buttonChooseRoom.setOnClickListener {
                        val intent = Intent(this, RoomActivity::class.java)
                        intent.putExtra("hotel_id", hotelId)
                        startActivity(intent)
                    }

                    binding.textHotelName.text = hotel.name
                    binding.textHotelAddress.text = hotel.adress
                    binding.ratingBarHotel.rating = hotel.rating?.let { it / 2.toFloat() } ?: 0f
                    binding.textRatingName.text = hotel.rating_name ?: ""
                    binding.textRatingNumber.text = hotel.rating.toString()
                    binding.textHotelPrice.text = "от ${hotel.minimal_price ?: ""}${hotel.price_for_it ?: ""}"

                    binding.textHotelName.setOnClickListener {
                        val bottomSheetFragment = HotelBottomSheetFragment.newInstance(hotel)
                        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
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

