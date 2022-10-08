package com.malikazizali.challengechapter5.fragment

import android.content.Context
import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.malikazizali.challengechapter5.R
import com.malikazizali.challengechapter5.adapter.MovieAdapter
import com.malikazizali.challengechapter5.databinding.FragmentHomeBinding
import com.malikazizali.challengechapter5.databinding.FragmentLoginBinding
import com.malikazizali.challengechapter5.databinding.FragmentRegisterBinding
import com.malikazizali.challengechapter5.network.RetrofitClientUser
import com.malikazizali.challengechapter5.viewmodel.MovieViewModel

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var sp : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginProgressBar.visibility = View.GONE

        sp = requireActivity().getSharedPreferences("loginstatus_ch5", Context.MODE_PRIVATE)
        val savedUsername = sp.getString("username", "")
        val savedPassword = sp.getString("password", "")

        binding.etUsername.setText(savedUsername)
        binding.etPassword.setText(savedPassword)

        binding.btnLogin.setOnClickListener {
            val usernameInput = binding.etUsername.text.toString()
            val passwordInput = binding.etPassword.text.toString()

            if(usernameInput.isEmpty()){
                binding.etUsername.requestFocus()
            }
            else if (passwordInput.isEmpty()){
                binding.etPassword.requestFocus()
            }
            else{
                login(view, usernameInput, passwordInput)
            }
        }

        binding.btnRegister.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun login(view : View, usernameInput : String, passwordInput : String){

        val viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        viewModel.callApiUser()
        viewModel.getLDUser().observe(viewLifecycleOwner, Observer {
            binding.loginProgressBar.visibility = View.VISIBLE
            if (it != null) {
                for (i in 0 until it.size){
                    if(it[i].username==usernameInput && it[i].password==passwordInput){

                        val addSession = sp.edit()
                        addSession.putString("id", it[i].id)
                        addSession.putString("namaLengkap", it[i].namaLengkap)
                        addSession.putString("username", usernameInput)
                        addSession.putString("password", passwordInput)
                        addSession.putString("session", "true")
                        addSession.apply()

                        Toast.makeText(requireActivity(), "Berhasil Login", Toast.LENGTH_SHORT).show()
                        binding.loginProgressBar.visibility = View.GONE
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    else{
                        binding.loginProgressBar.visibility = View.GONE
                    }
                }

            } else {
                binding.loginProgressBar.visibility = View.GONE
                Toast.makeText(context, "Belum ada data login", Toast.LENGTH_SHORT).show()
            }
        })
    }

}