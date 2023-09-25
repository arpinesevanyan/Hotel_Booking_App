package com.arpinesevanyan.hotelbookingapp.ui


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arpinesevanyan.hotelbookingapp.databinding.ActivityPaidBinding

class PaidActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonChooseRoom.setOnClickListener {
            val intent = Intent(this, HotelDetailActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}


