package com.matheussilas97.starwarsapp.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.api.response.CharactersDetailsResponse
import com.matheussilas97.starwarsapp.databinding.FragmentMainBinding
import com.matheussilas97.starwarsapp.utils.BaseFragment
import com.matheussilas97.starwarsapp.utils.Constants
import com.matheussilas97.starwarsapp.utils.PaginationScrollListener
import com.matheussilas97.starwarsapp.view.charactersdetails.DetailsActivity


class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    private var pageNumber = 1

    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.getListCharacter(requireContext())

        observer()


        binding.recyclerMain.setHasFixedSize(true)
        val layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
        binding.recyclerMain.layoutManager = layoutManager
        adapter = MainAdapter()

        adapter.clear()
        viewModel.clearCurrentPage()
        binding.recyclerMain.adapter = adapter


        binding.recyclerMain.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                if (viewModel.isLastPage()) {
                    viewModel.getListCharacter(requireContext())
                }else{
                    val a = ""
                }
            }

        })
        adapter.addOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onClick(url: String) {
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                intent.putExtra(Constants.URL, url)
                startActivity(intent)
            }

        })

        return binding.root
    }

    private fun observer() {
        viewModel.listCharacters.observe(viewLifecycleOwner, Observer {
            if (it != null) {
               adapter.updateTask(it.results)
            } else {
                setNoResultAdapter(binding.recyclerMain, "No characters found")
            }
        })
    }
}