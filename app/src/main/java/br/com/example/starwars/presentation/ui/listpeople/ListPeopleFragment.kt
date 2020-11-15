package br.com.example.starwars.presentation.ui.listpeople

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.starwars.databinding.FragmentListPeopleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPeopleFragment : Fragment() {

    private lateinit var binding: FragmentListPeopleBinding
    private val viewModel: ListPeopleViewModel by viewModels()
    private val adapterList: ListPeopleAdapter = ListPeopleAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentListPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        lifecycleScope.launch {
            viewModel.getList().collectLatest {
                adapterList.submitData(it)
            }
        }
    }

    private fun setupRecycler() {
        with(binding.recyclerList) {
            adapter = adapterList
            layoutManager = LinearLayoutManager(context)
        }
    }
}