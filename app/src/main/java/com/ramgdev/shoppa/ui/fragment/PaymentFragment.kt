package com.ramgdev.shoppa.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidstudy.daraja.Daraja
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.databinding.FragmentPaymentBinding

class PaymentFragment : BottomSheetDialogFragment() {

    private lateinit var daraja: Daraja
    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)



        return binding.root

    }
}