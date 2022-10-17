package com.ramgdev.shoppa.ui.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.databinding.FragmentPaymentBinding
import timber.log.Timber

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
//    private lateinit var daraja: Daraja

    private val daraja: Daraja = Daraja.with(
        "5aIbhQsfobZG4VMv8zuIRuwKyocTyhEU",
        "9Gd9YP4VlQHXaJeO", Env.SANDBOX,
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        /*daraja = Daraja.with(
            "gOwQ6TsvGmrCgtKs2jZGgqC0EHpK1xRK",
            "6OXAqARUSf5mzGXl",
            Env.SANDBOX,
            object : DarajaListener<AccessToken> {
                override fun onResult(result: AccessToken) {
                    Timber.d("Access Token ${result.access_token}")
                }

                override fun onError(error: String?) {
                    Timber.d(error.toString())
                }
            }
        )*/

        binding.buttonMpesa.setOnClickListener {

            try {
                pay(
                    "0708289107",
                    "1",
                    "Till Number"
                )
            } catch (e: Exception) {

            }

            /*val phoneNumber = "0742002867"
            val lnmExpress = LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                TransactionType.CustomerBuyGoodsOnline,
                "1",
                phoneNumber,
                "174379",
                phoneNumber,
                "",
                "001ABC",
                "Order Payment"

            )
            daraja.requestMPESAExpress(lnmExpress, object : DarajaListener<LNMResult> {
                override fun onResult(result: LNMResult) {
                    Toast.makeText(
                        requireContext(),
                        "Success: ${result.ResponseDescription}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(error: String?) {
                    Toast.makeText(requireContext(), "Error!: $error", Toast.LENGTH_SHORT).show()
                }
            })*/
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
            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
            TransactionType.CustomerPayBillOnline,
            amount,
            phone,
            "174379",
            phone,
            "https://mycallback.com",
            "001ABC",
            "Food Payment"
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