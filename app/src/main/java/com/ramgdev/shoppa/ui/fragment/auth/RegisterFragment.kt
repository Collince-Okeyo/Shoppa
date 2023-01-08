package com.ramgdev.shoppa.ui.fragment.auth

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.data.remote.model.User
import com.ramgdev.shoppa.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val userCollectionRef = Firebase.firestore.collection("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            registerWithEmailAndPassword()
        }

        binding.haveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        return binding.root
    }

    //Login with Email and Password
    private fun registerWithEmailAndPassword() {
        val email = binding.emailAddress.editText?.text.toString().trim()
        val password = binding.password.editText?.text.toString().trim()
        val name = binding.fullName.editText?.text.toString().trim()
        val phoneNumber = binding.phoneNumber.editText?.text.toString().trim()
        val user = User(
            name = name,
            email = email,
            phone = phoneNumber
        )

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Sign up")
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)

        when {
            binding.fullName.editText?.text.toString().isEmpty() -> {
                binding.fullName.editText?.error = "Required"
            }
            binding.emailAddress.editText?.text.toString().isEmpty() -> {
                binding.emailAddress.editText?.error = "Required"
            }
            binding.phoneNumber.editText?.text.toString().isEmpty() -> {
                binding.phoneNumber.editText?.error = "Required"
            }
            binding.password.editText?.text.toString().isEmpty() -> {
                binding.password.editText?.error = "Required"
            }

            else -> {
                progressDialog.show()
                binding.registerButton.isEnabled = false

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                            val firebaseUser = firebaseAuth.currentUser
                            firebaseUser!!.sendEmailVerification().await()
                            saveUserDetails(user)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Account created successfully. Please check your email to verify", Toast.LENGTH_LONG).show()
                                binding.fullName.editText?.setText("")
                                binding.emailAddress.editText?.setText("")
                                binding.password.editText?.setText("")
                                binding.phoneNumber.editText?.setText("")
                                progressDialog.hide()

                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()

                                progressDialog.hide()
                                binding.registerButton.isEnabled = true

                                binding.fullName.editText?.setText("")
                                binding.emailAddress.editText?.setText("")
                                binding.password.editText?.setText("")
                                binding.phoneNumber.editText?.setText("")
                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Invalid Email! Please enter a valid email account", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //saving user details
    private fun saveUserDetails(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            userCollectionRef.add(user).await()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}