package com.github.weslleystos.features.list.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.github.weslleystos.MainActivity
import com.github.weslleystos.R
import com.github.weslleystos.databinding.FragmentPeopleListBinding
import com.github.weslleystos.features.list.viewmodel.PeopleListViewModel
import com.github.weslleystos.shared.entities.People
import com.github.weslleystos.shared.utils.LiveDateState
import com.github.weslleystos.shared.utils.hide
import com.github.weslleystos.shared.utils.show

class PeoplesList : Fragment() {
    var isSearching = false
    private var currentPage = 0
    private var isLoading = false
    private lateinit var peoplesAdapter: PeoplesAdapter
    private var _binding: FragmentPeopleListBinding? = null
    val peoplesViewModel: PeopleListViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPage = 0
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.fragmet_list_people_title)

        val linearLayoutManager = LinearLayoutManager(context)
        peoplesAdapter = PeoplesAdapter(this)

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        val recyclerView = binding.recyclerPeoples
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = peoplesAdapter
            addItemDecoration(divider)
            addOnScrollListener(endlessScrolling)
        }

        binding.searchBar.addTextChangedListener(searchTextWatch)

        peoplesViewModel.peoplesLiveData.observe(viewLifecycleOwner, observer)

        peoplesViewModel.getAll(currentPage)
    }

    private val observer = Observer<Pair<LiveDateState, List<People>?>> { response ->
        isLoading = false
        binding.progressCircular.hide()
        when (response.first) {
            LiveDateState.FETCHED -> {
                currentPage++
                peoplesAdapter.setPeoples(response.second!!)
            }
            LiveDateState.FAILED -> {
                Toast.makeText(
                    context,
                    getString(R.string.peoples_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun fetchPeoples() {
        isLoading = true
        binding.progressCircular.show()
        peoplesViewModel.getAll(currentPage)
    }

    private val endlessScrolling = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val layoutManager = recyclerView.layoutManager!! as LinearLayoutManager
            val lastItem = layoutManager.findLastVisibleItemPosition() + 1
            val totalItems = layoutManager.itemCount

            if (!isLoading && !isSearching && lastItem == totalItems) {
                fetchPeoples()
            }
        }
    }

    private val searchTextWatch = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            peoplesAdapter.setFilter(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}