package com.malikazizali.challengechapter5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.malikazizali.challengechapter5.R
import com.malikazizali.challengechapter5.databinding.FragmentRegisterBinding
import com.malikazizali.challengechapter5.viewmodel.MovieViewModel

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    lateinit var sp : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerProgressBar.visibility = View.GONE
        sp = requireActivity().getSharedPreferences("loginstatus_ch5", Context.MODE_PRIVATE)

        val usernameInput = binding.etUsername.text.toString()
        val namaLengkapInput = binding.etNamaLengkap.text.toString()
        val passwordInput = binding.etPassword.text.toString()
        val conPassInput = binding.etConPassword.text.toString()

        binding.btnRegister.setOnClickListener {
            if(usernameInput.isNotEmpty() && namaLengkapInput.isNotEmpty() && passwordInput.isNotEmpty() && conPassInput.isNotEmpty()) {
                if (passwordInput == conPassInput) {
                    addNewUser(namaLengkapInput, usernameInput, passwordInput)
                    if (binding.registerProgressBar.visibility==View.GONE)
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    Toast.makeText(requireActivity(), "Password tidak sesuai !", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    fun addNewUser(namaLengkap : String, username : String, pass : String){
        binding.registerProgressBar.visibility = View.VISIBLE
        val vm = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        vm.callApiAddUser(namaLengkap, username, pass)
        vm.getLDNewUser().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(
                    requireActivity(),
                    "Berhasil mendaftarkan akun baru !",
                    Toast.LENGTH_SHORT
                ).show()

                val addRegCookies = sp.edit()
                addRegCookies.putString("namaLengkap", namaLengkap)
                addRegCookies.putString("username", username)
                addRegCookies.putString("password", pass)
                addRegCookies.apply()
                binding.registerProgressBar.visibility = View.GONE
            } else {
                binding.registerProgressBar.visibility = View.GONE
                Toast.makeText(
                    requireActivity(),
                    "Gagal mendaftarkan akun baru !",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}