package com.ramgdev.shoppa.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.databinding.FragmentForgotPasswordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        binding.textViewConfirm.setOnClickListener {
//            Utils.hideKeyboard(it)

            if (binding.editTextTextEmailAddress.text.toString().isEmpty()) {
                binding.editTextTextEmailAddress.error = "Enter email address"
            } else {
            }
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    FirebaseAuth.getInstance()
                        .sendPasswordResetEmail(binding.editTextTextEmailAddress.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    requireContext(),
                                    "Please check your email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } catch (e : Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.textViewCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}