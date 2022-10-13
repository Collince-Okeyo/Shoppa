package com.ramgdev.shoppa.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.paystack.android.Paystack
import co.paystack.android.PaystackSdk
import co.paystack.android.PaystackSdk.applicationContext
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.ramgdev.shoppa.BuildConfig
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.databinding.FragmentPayStackBinding
import com.ramgdev.shoppa.util.CreditCardFormatter
import com.ramgdev.shoppa.util.NetworkUtility
import org.json.JSONException
import timber.log.Timber

class PayStackFragment : Fragment() {

    private var charge: Charge? = null
    private lateinit var binding: FragmentPayStackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPayStackBinding.inflate(inflater, container, false)

        initializePaystack()
        initViews()

        return binding.root
    }
    // Initialize all views here

    private fun initViews() {
        //add text watcher to edit text fields
        addTextWatcherToEditText()

        //set the amount to pay in the button, this could be dynamic, that is why it wasn't added in the activity layout
        val totalPrice = "100.56$"
        binding.btnPay.text = parentFragment?.getString(R.string.pay_amount, totalPrice)

        //handle clicks here
        handleClicks()
    }

    // Add formatting to card number, cvv, and expiry date
    private fun addTextWatcherToEditText() {

        //Make button UnClickable for the first time
        binding.btnPay.isEnabled = false
        binding.btnPay.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.btn_round_opaque)

        //make the button clickable after detecting changes in input field
        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                val s1 = binding.etCardNumber.text.toString()
                val s2 = binding.etExpiry.text.toString()
                val s3 = binding.etCvv.text.toString()

                //check if they are empty, make button unclickable
                if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
                    binding.btnPay.isEnabled = false
                    binding.btnPay.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.btn_round_opaque
                    )
                }

                //check the length of all edit text, if meet the required length, make button clickable
                if (s1.length >= 16 && s2.length == 5 && s3.length == 3) {
                    binding.btnPay.isEnabled = true
                    binding.btnPay.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.btn_border_blue_bg
                    )
                }

                //if edit text doesn't meet the required length, make button unclickable. You could use else statement from the above if
                if (s1.length < 16 || s2.length < 5 || s3.length < 3) {
                    binding.btnPay.isEnabled = false
                    binding.btnPay.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.btn_round_opaque
                    )
                }

                //add a slash to expiry date after first two character(month)
                if (s2.length == 2) {
                    if (start == 2 && before == 1 && !s2.contains("/")) {
                        binding.etExpiry.setText(
                            parentFragment?.getString(
                                R.string.expiry_space,
                                s2[0]
                            )
                        );
                        binding.etExpiry.setSelection(1)
                    } else {
                        binding.etExpiry.setText(
                            parentFragment?.getString(
                                R.string.expiry_slash,
                                s2
                            )
                        );
                        binding.etExpiry.setSelection(3)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        }
        //add text watcher
        binding.etCardNumber.addTextChangedListener(CreditCardFormatter()) //helper class for card number formatting
        binding.etExpiry.addTextChangedListener(watcher)
        binding.etCvv.addTextChangedListener(watcher)
    }

    private fun handleClicks() {

        //on click of pay button
        binding.btnPay.setOnClickListener {

            //here you can check for network availability first, if the network is available, continue
            if (NetworkUtility.isNetworkAvailable(applicationContext)) {

                //show loading
                binding.loadingPayOrder.visibility = View.VISIBLE
                binding.btnPay.visibility = View.GONE

                //perform payment
                doPayment()

            } else {

                Toast.makeText(requireContext(), "Please check your internet", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun doPayment() {

        // initialize the charge
        charge = Charge()
        charge!!.card = loadCardFromForm()

        charge!!.amount = 1
        charge!!.email = "ramgjunior@gmail.com"
//        charge!!.reference = "payment" + Calendar.getInstance().timeInMillis

        try {
            charge!!.putCustomField("Charged From", "Android SDK")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        doChargeCard()
    }

    private fun loadCardFromForm(): Card {

        //validate fields
        val cardNumber = binding.etCardNumber.text.toString().trim()
        val expiryDate = binding.etExpiry.text.toString().trim()
        val cvc = binding.etCvv.text.toString().trim()

        //formatted values
        val cardNumberWithoutSpace = cardNumber.replace(" ", "")
        val monthValue = expiryDate.substring(0, expiryDate.length.coerceAtMost(2))
        val yearValue = expiryDate.takeLast(2)

        //build card object with ONLY the number, update the other fields later
        val card: Card = Card.Builder(cardNumberWithoutSpace, 0, 0, "").build()

        //update the cvc field of the card
        card.cvc = cvc

        //validate expiry month;
        val sMonth: String = monthValue
        var month = 0
        try {
            month = sMonth.toInt()
        } catch (ignored: Exception) { }

        card.expiryMonth = month

        //validate expiry year
        val sYear: String = yearValue
        var year = 0
        try {
            year = sYear.toInt()
        } catch (ignored: Exception) { }
        card.expiryYear = year

        return card

    }

    private fun doChargeCard() {

        PaystackSdk.chargeCard(requireActivity(), charge, object : Paystack.TransactionCallback {

            // This is called only after transaction is successful
            override fun onSuccess(transaction: Transaction) {

                //hide loading
                binding.loadingPayOrder.visibility = View.GONE
                binding.btnPay.visibility = View.VISIBLE
                parseResponse(transaction.reference)
            }

            override fun beforeValidate(transaction: Transaction) {

                Timber.d(transaction.reference)

            }

            override fun onError(error: Throwable, transaction: Transaction) {

                //stop loading
                binding.loadingPayOrder.visibility = View.GONE
                binding.btnPay.visibility = View.VISIBLE

                Timber.d("Error: ${error.localizedMessage}")
            }
        })
    }

    private fun parseResponse(transactionReference: String) {
        val message = "Payment Successful - $transactionReference"
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        binding.loadingPayOrder.visibility = View.GONE
    }

    private fun initializePaystack() {
        PaystackSdk.initialize(applicationContext)
        PaystackSdk.setPublicKey(BuildConfig.PSTK_PUBLIC_KEY)
    }
}