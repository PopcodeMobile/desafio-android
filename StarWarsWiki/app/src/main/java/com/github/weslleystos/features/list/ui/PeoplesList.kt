package com.github.weslleystos.features.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.github.weslleystos.R
import com.github.weslleystos.databinding.FragmentPeopleListBinding
import com.github.weslleystos.features.list.viewmodel.PeopleListViewModel
import com.github.weslleystos.shared.utils.hide
import com.github.weslleystos.shared.utils.show

class PeoplesList : Fragment() {
    private var currentPage = 0
    private var isLoading = false
    private lateinit var progressCircular: ProgressBar
    private var _binding: FragmentPeopleListBinding? = null
    private val peoplesViewModel: PeopleListViewModel by viewModels()

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
        val linearLayoutManager = LinearLayoutManager(context)
        val peoplesAdapter = PeoplesAdapter()

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_peoples)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = peoplesAdapter
            addItemDecoration(divider)
            addOnScrollListener(endlessScrolling)
        }

        progressCircular = view.findViewById(R.id.progress_circular)

        peoplesViewModel.peoplesLiveData.observe(viewLifecycleOwner, { response ->
            isLoading = false
            progressCircular.hide()
            when {
                response.first -> {
                    currentPage++
                    peoplesAdapter.setPeoples(response.second!!)
                }
                else -> {
                    Toast.makeText(
                        context,
                        getString(R.string.peoples_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        peoplesViewModel.getAll(currentPage)
    }

    private fun fetchPeoples() {
        isLoading = true
        progressCircular.show()
        peoplesViewModel.getAll(currentPage)
    }

    private val endlessScrolling = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val layoutManager = recyclerView.layoutManager!! as LinearLayoutManager
            val lastItem = layoutManager.findLastVisibleItemPosition() + 1
            val totalItems = layoutManager.itemCount

            if (!isLoading && lastItem == totalItems) {
                fetchPeoples()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}