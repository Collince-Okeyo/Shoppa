package com.ramgdev.shoppa.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ramgdev.shoppa.R
import com.ramgdev.shoppa.databinding.FragmentSplashBinding
import com.ramgdev.shoppa.util.NetworkUtility

class SplashFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        /*Handler().postDelayed({
                if (user == null && NetworkUtility.isNetworkAvailable(requireContext())) {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
        }, 2000)*/

        Handler().postDelayed({
            if (NetworkUtility.isNetworkAvailable(requireContext())) {
                if (user == null && NetworkUtility.isNetworkAvailable(requireContext())) {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
            }else {
                binding.imageView5.isVisible = true
                binding.textView6.isVisible = true
                binding.imageView.isVisible = false
            }
        }, 2000)

        return binding.root
    }

}