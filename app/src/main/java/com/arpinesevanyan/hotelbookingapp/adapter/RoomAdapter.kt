package com.arpinesevanyan.hotelbookingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.arpinesevanyan.hotelbookingapp.data.RoomX
import com.arpinesevanyan.hotelbookingapp.databinding.ItemRoomBinding

class RoomAdapter(private val rooms: List<RoomX>, private val onChooseRoomClickListener: OnChooseRoomClickListener) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    interface OnChooseRoomClickListener {
        fun onChooseRoomClick(room: RoomX)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomBinding.inflate(inflater, parent, false)
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.bind(room)
        holder.chooseRoomButton.setOnClickListener {
            onChooseRoomClickListener.onChooseRoomClick(room)
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    inner class RoomViewHolder(private val binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(room: RoomX) {
            binding.room = room
            binding.executePendingBindings()

            val imageUrls = room.image_urls

            val imageSliderAdapter = ImageRoomSliderAdapter(imageUrls)
            binding.roomViewPager.adapter = imageSliderAdapter
        }

        val chooseRoomButton: Button = binding.chooseRoomButton
    }
}
