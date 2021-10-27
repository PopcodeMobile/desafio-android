package com.matheussilas97.starwarsapp.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.api.response.CharactersDetailsResponse
import com.matheussilas97.starwarsapp.databinding.FragmentMainBinding
import com.matheussilas97.starwarsapp.utils.BaseFragment
import com.matheussilas97.starwarsapp.utils.Constants
import com.matheussilas97.starwarsapp.view.charactersdetails.DetailsActivity


class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private var pageNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        observer()

        return binding.root
    }

    private fun observer() {
        viewModel.listCharacters(pageNumber, requireContext())
            .observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    buildList(it.results)
                } else {
                    setNoResultAdapter(binding.recyclerMain, "No characters found")
                }
            })
    }

    private fun buildList(list: List<CharactersDetailsResponse>) {
        val adapter = MainAdapter()
        binding.recyclerMain.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMain.adapter = adapter
        adapter.updateTask(list)
        adapter.addOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onClick(url: String) {
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                intent.putExtra(Constants.URL, url)
                startActivity(intent)
            }

        })
    }


}