package com.matheussilas97.starwarsapp.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        buildList()

        return binding.root
    }


    private fun buildList() {
        val adapter = MainAdapter()
        binding.recyclerMain.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMain.adapter = adapter
        adapter.updateTask()
//        adapter.addOnItemClickListener(MainAdapter.OnItemClickListener)
    }


}