package com.arpinesevanyan.hotelbookingapp.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.arpinesevanyan.hotelbookingapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TouristFormDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_tourist_dialog, container, false)
    }

}

