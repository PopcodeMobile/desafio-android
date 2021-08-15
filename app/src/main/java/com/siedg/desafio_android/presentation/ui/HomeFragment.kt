package com.siedg.desafio_android.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.siedg.desafio_android.R
import com.siedg.desafio_android.data.model.PersonModel
import com.siedg.desafio_android.databinding.FragmentHomeBinding
import com.siedg.desafio_android.presentation.di.Injector
import com.siedg.desafio_android.presentation.viewmodel.HomeViewModel
import com.siedg.desafio_android.presentation.viewmodel.HomeViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var factory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val groupAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as Injector).createHomeSubComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        viewModel = ViewModelProvider(this, factory)
            .get(HomeViewModel::class.java)

        val responseLiveData = viewModel.getPersonList()

        responseLiveData.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                groupAdapter.clear()
                groupAdapter.addAll(it.toRecyclerViewListItem())
                binding.rvHomeList.adapter = groupAdapter
            }
        })
        return binding.root
    }

    private fun List<PersonModel>.toRecyclerViewListItem(): List<HomeItem> {
        return this.map { item -> HomeItem(item) }
    }
}