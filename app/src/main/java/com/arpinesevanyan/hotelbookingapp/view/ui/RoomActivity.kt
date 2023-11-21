package com.arpinesevanyan.hotelbookingapp.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.arpinesevanyan.hotelbookingapp.R
import com.arpinesevanyan.hotelbookingapp.view.adapter.RoomAdapter
import com.arpinesevanyan.hotelbookingapp.model.network.RetrofitClient
import com.arpinesevanyan.hotelbookingapp.model.network.RoomResponse
import com.arpinesevanyan.hotelbookingapp.model.data.RoomX
import com.arpinesevanyan.hotelbookingapp.databinding.ActivityRoomBinding
import com.arpinesevanyan.hotelbookingapp.model.data.HotelData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var roomAdapter: RoomAdapter
    private val roomList: MutableList<RoomX> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)

        val hotel: HotelData? = intent.getSerializableExtra("hotel") as? HotelData

        val onChooseRoomClickListener = object : RoomAdapter.OnChooseRoomClickListener {
            override fun onChooseRoomClick(room: RoomX) {
                onChooseRoom(room)
            }
        }

        roomAdapter = RoomAdapter(roomList, onChooseRoomClickListener)
        binding.imageRecyclerView.adapter = roomAdapter
        binding.imageRecyclerView.layoutManager = LinearLayoutManager(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (hotel == null) {
            Log.e("RoomActivity", "Hotel is null")
        } else {
            supportActionBar?.title = hotel.name
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        loadRoomDataFromApi()
    }

    private fun loadRoomDataFromApi() {
        val apiService = RetrofitClient.createHotelApiService()
        val call = apiService.getRooms()

        call.enqueue(object : Callback<RoomResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<RoomResponse>, response: Response<RoomResponse>) {
                if (response.isSuccessful) {
                    val roomResponse = response.body()
                    if (roomResponse != null) {
                        val rooms = roomResponse.rooms
                        roomList.addAll(rooms)
                        roomAdapter.notifyDataSetChanged()
                    }
                } else {
                    Log.e("RoomActivity", "API call failed with response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                Log.e("RoomActivity", "API call failed with error: ${t.message}")
            }
        })
    }

    private fun onChooseRoom(room: RoomX) {
        val intent = Intent(this, BookingActivity::class.java)
        intent.putExtra("rooms", room)
        startActivity(intent)
    }
}
