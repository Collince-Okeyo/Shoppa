package com.ramgdev.shoppa.ui.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.databinding.FragmentPaymentBinding
import timber.log.Timber

class PaymentFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPaymentBinding

    private val daraja: Daraja = Daraja.with(
        "fkgASeWSL1NP3aEj07CLuAjuP9TQez1S",
        "c6RtBlH0cHiwImCD", Env.SANDBOX,
        object : DarajaListener<AccessToken> {
            override fun onResult(result: AccessToken) {
                Timber.d("Access Token: ${result.access_token}")
            }
            override fun onError(error: String?) {
                Timber.d("Error: $error")
            }
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        binding.buttonMpesa.setOnClickListener {

            try {
                pay(
                    "254708289107",
                    "1",
                    "Till Number"
                )
            } catch (e: Exception) {
                Timber.d("")
            }
        }
        binding.buttonCard.setOnClickListener {
            Toast.makeText(requireContext(), "CLICKED", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_paymentFragment2_to_payStackFragment)
        }

        return binding.root
    }

    private fun pay(phone: String, amount: String, tillNoOrPaybillNo: String) {

        val lnmExpress = LNMExpress(
            "174379",
            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
            TransactionType.CustomerPayBillOnline,
            "1",
            "254742002867",
            "174379",
            phone,
            "https://usafi-48370-default-rtdb.firebaseio.com/",
            "001ABC",
            "Paybill option"
        )

        daraja.requestMPESAExpress(lnmExpress, object :
            DarajaListener<LNMResult> {
            override fun onResult(result: LNMResult) {
                Toast.makeText(
                    requireContext(),
                    "Response here ${result.ResponseDescription}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onError(error: String?) {
                Toast.makeText(
                    requireContext(),
                    "Error here $error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}