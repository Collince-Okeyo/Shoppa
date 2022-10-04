package com.ramgdev.shoppa.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        //Sign in with Email and Password
        binding.signinButton.setOnClickListener { it ->
//            Utils.hideKeyboard(it)
            logInWithEmailAndPassword()
        }

        binding.forgotPass.setOnClickListener {
            val forgotPassword = ForgotPasswordFragment()
            forgotPassword.show(childFragmentManager, "dialog_reset_password")
        }

        binding.dontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding.root
    }

    //Login with email and password
    private fun logInWithEmailAndPassword() {
        val email: String = binding.emailEditText.editText?.text.toString()
        val password: String = binding.passwordEditText.editText?.text.toString()

        when {
            binding.emailEditText.editText?.text.toString().isEmpty() -> {
                binding.emailEditText.editText?.error = "Email required"
            }
            binding.passwordEditText.editText?.text.toString().isEmpty() -> {
                binding.passwordEditText.editText?.error = "Password required"
            }
            else -> {
                binding.loginProgressBar.visibility = VISIBLE
                binding.signinButton.isEnabled = false

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            firebaseAuth.signInWithEmailAndPassword(email, password).await()

                            withContext(Dispatchers.Main) {
                                binding.loginProgressBar.visibility = GONE
                                binding.signinButton.isEnabled = true
                                binding.emailEditText.editText?.setText("")
                                binding.passwordEditText.editText?.setText("")

                                val firebaseUser = firebaseAuth.currentUser
                                if (firebaseUser!!.isEmailVerified) {
                                    Toast.makeText(requireContext(),"Logged in Successfully", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                                } else {
                                    Toast.makeText(requireContext(),"Please verify your email first", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                                binding.loginProgressBar.visibility = GONE
                                binding.signinButton.isEnabled = true
                                binding.emailEditText.editText?.setText("")
                                binding.passwordEditText.editText?.setText("")
                            }
                        }
                    }
                }
            }
        }
    }
}