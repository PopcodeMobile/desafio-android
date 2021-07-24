package com.example.desafio_android.ui.main.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.desafio_android.R
import com.example.desafio_android.databinding.FragmentPeopleBinding
import com.example.desafio_android.ui.main.viewmodel.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    val peopleViewModel: PeopleViewModel by viewModels()

    lateinit var _bindingPeople: FragmentPeopleBinding
    val bindingMovie: FragmentPeopleBinding get() = _bindingPeople

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _bindingPeople = FragmentPeopleBinding.inflate(inflater, container, false)

        peopleViewModel.people.observe(viewLifecycleOwner) { people ->
            // Criar Adapter
        }

        setHasOptionsMenu(true)

        return bindingMovie.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

}