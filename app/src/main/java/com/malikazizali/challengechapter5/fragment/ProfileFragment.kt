package com.malikazizali.challengechapter5.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.malikazizali.challengechapter5.MainActivity
import com.malikazizali.challengechapter5.R
import com.malikazizali.challengechapter5.databinding.FragmentProfileBinding
import com.malikazizali.challengechapter5.viewmodel.MovieViewModel
import java.util.*

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var sp: SharedPreferences
    var currLang = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = requireActivity().getSharedPreferences("loginstatus_ch5", Context.MODE_PRIVATE)

        getSavedData()

        binding.arrowBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.changeLanguage.setOnClickListener {
            getLocale()
            openChangeLangDialog(view, currLang)
        }

        binding.ivEdit.setOnClickListener {
            enableEditMode()
            binding.btnEdit.visibility = View.VISIBLE
            binding.btnEdit.setOnClickListener {
                val savedId = sp.getString("id", "")
                val newNamaLengkap = binding.etNamaLengkap.text.toString()
                val newUsername = binding.etUsername.text.toString()
                val newPassword = binding.etPassword.text.toString()
                updateUserData(savedId!!, newNamaLengkap, newUsername, newPassword)
                disableEditMode()
                binding.btnEdit.visibility = View.GONE
            }
            getSavedData()
        }

        binding.btnLogout.setOnClickListener {
            val clearSession = sp.edit()
            clearSession.clear()
            clearSession.apply()

            Navigation.findNavController(view)
                .navigate(R.id.action_profileFragment_to_loginFragment)
        }

    }

    private fun getSavedData() {
        val savedNama = sp.getString("namaLengkap", "nama")
        val savedUsername = sp.getString("username", "username")
        val savedPassword = sp.getString("password", "password")

        binding.etNamaLengkap.setText(savedNama)
        binding.etUsername.setText(savedUsername)
        binding.etPassword.setText(savedPassword)
    }

    fun enableEditMode() {
        binding.etNamaLengkap.isEnabled = true
        binding.etUsername.isEnabled = true
        binding.password.isEnabled = true
    }

    fun disableEditMode() {
        binding.etNamaLengkap.isEnabled = false
        binding.etUsername.isEnabled = false
        binding.password.isEnabled = false
    }

    fun updateUserData(id: String, namaLengkap: String, username: String, password: String) {
        val vm = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        vm.callApiEditUser(id, namaLengkap, username, password)
        vm.getLDUpdateUser().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(
                    requireActivity(), "Berhasil memperbarui data profil !", Toast.LENGTH_SHORT
                ).show()

                val updateCookies = sp.edit()
                updateCookies.putString("namaLengkap", namaLengkap)
                updateCookies.putString("username", username)
                updateCookies.putString("password", password)
                updateCookies.apply()
            }
        })
    }

    fun setLocale(lang: String) {
//        val myLocale = Locale(lang)
//        val res = resources
//        val conf = res.configuration
//        conf.locale = myLocale
//        res.updateConfiguration(conf, res.displayMetrics)

        val locale = Locale(lang)
        Locale.setDefault(locale)

        val resources = context?.resources

        val configuration = resources?.configuration
        configuration?.locale = locale
        configuration?.setLayoutDirection(locale)

        resources?.updateConfiguration(configuration, resources.displayMetrics)
    }

    fun getLocale() {
        val lang = resources.getConfiguration().locale.getLanguage();
        currLang = lang
    }

    fun openChangeLangDialog(view: View, currLang: String) {
        if (currLang == "id") {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Ganti Bahasa")
            builder.setMessage("Ganti bahasa menjadi bahasa inggris ?\nAplikasi akan direstart untuk menerapkan perubahan")
            builder.setIcon(R.drawable.ic_baseline_language_24_black)
            builder.setPositiveButton("Korfimasi") { _, _ ->
                setLocale("en")
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
            builder.setNegativeButton("Batalkan") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        else if (currLang == "en") {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Change Language")
            builder.setMessage("Change the language to Bahasa ?\nThe app will be restarted to apply the changes")
            builder.setIcon(R.drawable.ic_baseline_language_24_black)
            builder.setPositiveButton("Confirm") { _, _ ->
                setLocale("id")
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

}