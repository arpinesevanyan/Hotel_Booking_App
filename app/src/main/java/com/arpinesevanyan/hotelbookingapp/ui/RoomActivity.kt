package com.arpinesevanyan.hotelbookingapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.arpinesevanyan.hotelbookingapp.R
import com.arpinesevanyan.hotelbookingapp.adapter.RoomAdapter
import com.arpinesevanyan.hotelbookingapp.api.RetrofitClient
import com.arpinesevanyan.hotelbookingapp.api.RoomResponse
import com.arpinesevanyan.hotelbookingapp.data.RoomX
import com.arpinesevanyan.hotelbookingapp.databinding.ActivityRoomBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private lateinit var roomAdapter: RoomAdapter
    private val rooms: MutableList<RoomX> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)

        val onChooseRoomClickListener = object : RoomAdapter.OnChooseRoomClickListener {
            override fun onChooseRoomClick(room: RoomX) {
                onChooseRoom(room)
            }
        }

        roomAdapter = RoomAdapter(rooms, onChooseRoomClickListener)
        binding.imageRecyclerView.adapter = roomAdapter
        binding.imageRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        loadRoomDataFromApi()
    }

    private fun loadRoomDataFromApi() {
        val apiService = RetrofitClient.createHotelApiService()
        val call = apiService.getRooms()

        call.enqueue(object : Callback<RoomResponse> {
            override fun onResponse(call: Call<RoomResponse>, response: Response<RoomResponse>) {
                if (response.isSuccessful) {
                    val roomResponse = response.body()
                    if (roomResponse != null) {
                        val roomList = roomResponse.rooms
                        rooms.addAll(roomList)
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
        intent.putExtra("room", room)
        startActivity(intent)
    }
}
