package com.example.desafio_android.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
    ): View? {

        _bindingPeople = FragmentPeopleBinding.inflate(inflater, container, false)

        setupRecyclerView()

        requestGetPeople()

        setHasOptionsMenu(true)

        return bindingMovie.root

    }

    private fun requestGetPeople() {
        lifecycleScope.launch {
            peopleViewModel.people.observe(viewLifecycleOwner) { people ->
                adapter.submitData(viewLifecycleOwner.lifecycle, people)
            }
        }
    }

    private fun setupRecyclerView() {
        bindingMovie.recyclerPeople.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

}