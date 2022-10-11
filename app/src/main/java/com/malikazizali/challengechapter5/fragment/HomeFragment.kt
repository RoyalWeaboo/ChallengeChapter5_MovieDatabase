package com.malikazizali.challengechapter5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.malikazizali.challengechapter5.R
import com.malikazizali.challengechapter5.adapter.MovieAdapter
import com.malikazizali.challengechapter5.databinding.FragmentHomeBinding
import com.malikazizali.challengechapter5.viewmodel.MovieViewModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: MovieAdapter
    lateinit var sp : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = requireActivity().getSharedPreferences("loginstatus_ch5", Context.MODE_PRIVATE)
        val savedNama = sp.getString("namaLengkap", "user")
        binding.greetingText.text = savedNama

        setViewModelToAdapter()

        binding.ivProfile.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment)
        }

    }

    private fun setViewModelToAdapter() {
        val viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        viewModel.getLDMovie().observe(viewLifecycleOwner, Observer {
            viewModel.loading.observe(viewLifecycleOwner, Observer {
                when (it) {
                    true -> binding.homeProgressBar.visibility = View.VISIBLE
                    false -> binding.homeProgressBar.visibility = View.GONE
                }
            })

            if (it != null) {
                binding.rvMovie.layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )

                adapter = MovieAdapter(it.results)
                binding.rvMovie.adapter = adapter

            } else {
                Toast.makeText(requireActivity(), context?.getString(R.string.no_data), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.callApiFilm()
    }

}