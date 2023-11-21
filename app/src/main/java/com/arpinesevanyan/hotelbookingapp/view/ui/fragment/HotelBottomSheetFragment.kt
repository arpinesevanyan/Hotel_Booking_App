package com.arpinesevanyan.hotelbookingapp.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.arpinesevanyan.hotelbookingapp.databinding.BottomSheetHotelBinding
import com.arpinesevanyan.hotelbookingapp.model.data.HotelData

class HotelBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetHotelBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hotel: HotelData? = arguments?.getSerializable("hotel") as? HotelData
        hotel?.let { populateBottomSheet(it) }
    }

    private fun populateBottomSheet(hotel: HotelData) {
        binding.textHotelDescription.text = hotel.about_the_hotel?.description
        binding.textHotelPeculiarities.text =
            hotel.about_the_hotel?.peculiarities?.joinToString(", ")
    }

    companion object {
        fun newInstance(hotel: HotelData): HotelBottomSheetFragment {
            val fragment = HotelBottomSheetFragment()
            val args = Bundle()
            args.putSerializable("hotel", hotel)
            fragment.arguments = args
            return fragment
        }
    }
}

