package com.example.desafio_android.ui.main.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.desafio_android.R
import com.example.desafio_android.databinding.FragmentPeopleBinding
import com.example.desafio_android.ui.main.adapter.PeopleAdapter
import com.example.desafio_android.ui.main.viewmodel.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    val adapter by lazy { PeopleAdapter() }

    val peopleViewModel: PeopleViewModel by viewModels()

    lateinit var _bindingPeople: FragmentPeopleBinding
    val bindingMovie: FragmentPeopleBinding get() = _bindingPeople

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _bindingPeople = FragmentPeopleBinding.inflate(inflater, container, false)

        setupRecyclerView()

        requestGetPeople()

        loadState()

        setHasOptionsMenu(true)

        return bindingMovie.root

    }

    private fun setupRecyclerView() {
        bindingMovie.recyclerPeople.adapter = adapter
    }

    private fun requestGetPeople() {
        lifecycleScope.launch {
            peopleViewModel.people.observe(viewLifecycleOwner) { people ->
                adapter.submitData(viewLifecycleOwner.lifecycle, people)
            }
        }
    }

    private fun loadState() {
        adapter.addLoadStateListener { loadState ->
            bindingMovie.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            if (loadState.source.refresh is LoadState.Error) {
                if (!peopleViewModel.hasInternetConnection()) {
                    // SEM NET
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)

        val search = menu.findItem(R.id.searchPeople)
        val searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.setBackgroundColor(Color.WHITE)

        searchView.queryHint = getString(R.string.pesquise_um_personagem)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    peopleViewModel.searchPeople(newText)
                }
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

}