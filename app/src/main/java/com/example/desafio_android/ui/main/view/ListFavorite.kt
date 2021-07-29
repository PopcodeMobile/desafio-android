package com.example.desafio_android.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.desafio_android.databinding.FragmentListFavoriteBinding
import com.example.desafio_android.ui.main.adapter.ListFavoriteAdapter
import com.example.desafio_android.ui.main.viewmodel.ListFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFavorite : Fragment() {

    val adapterListFavorite by lazy { ListFavoriteAdapter() }

    val listFavoriteViewModel: ListFavoriteViewModel by viewModels()

    lateinit var _bindingFavorite: FragmentListFavoriteBinding
    val bindingFavorite: FragmentListFavoriteBinding get() = _bindingFavorite

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _bindingFavorite = FragmentListFavoriteBinding.inflate(inflater, container, false)

        setupRecyclerView()

        getFavorites()

        return bindingFavorite.root
    }

    private fun getFavorites() {
        listFavoriteViewModel.getFavorites.observe(viewLifecycleOwner) { people ->
            adapterListFavorite.submitList(people)
            if (people.isEmpty()){
                bindingFavorite.imageViewClear.isVisible = true
            }
        }
    }

    private fun setupRecyclerView() {
        bindingFavorite.recyclerFavorite.adapter = adapterListFavorite
    }


}