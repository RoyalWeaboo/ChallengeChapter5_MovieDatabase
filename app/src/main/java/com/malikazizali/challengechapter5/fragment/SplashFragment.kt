package com.malikazizali.challengechapter5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.malikazizali.challengechapter5.R
import com.malikazizali.challengechapter5.databinding.FragmentSplashScreenBinding

class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashScreenBinding
    lateinit var sp : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = requireContext().getSharedPreferences("loginstatus_ch5", Context.MODE_PRIVATE)
        val status = sp.getString("session","false")

        Handler().postDelayed({
            if(status=="false")
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment)
            else if(status=="true")
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment)
        }, 3000)
    }
}


