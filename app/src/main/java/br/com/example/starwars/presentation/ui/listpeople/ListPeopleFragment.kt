package br.com.example.starwars.presentation.ui.listpeople

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.starwars.R
import br.com.example.starwars.databinding.FragmentListPeopleBinding
import br.com.example.starwars.domain.entities.People
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class ListPeopleFragment : Fragment() {

    private lateinit var binding: FragmentListPeopleBinding
    private val viewModel: ListPeopleViewModel by viewModels()
    private val adapterList: ListPeopleAdapter =
        ListPeopleAdapter(::callbackClick, ::callbackFavorite)
    val controller by lazy { findNavController() }
    lateinit var direction: NavDirections

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentListPeopleBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        listAll()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_all -> {
                listAll()
            }
            R.id.list_favorites -> {
                listFavorites()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun listAll() {
        lifecycleScope.launch {
            viewModel.getList().collectLatest {
                adapterList.submitData(it)
            }
        }
    }

    private fun listFavorites() {
        lifecycleScope.launch {
            viewModel.listFavorites().collectLatest {
                adapterList.submitData(it)
            }
        }
    }

    private fun callbackClick(people: People) {
        direction =
            ListPeopleFragmentDirections.actionListPeopleFragmentToPeopleDetailFragment(people)
        controller.navigate(direction)
    }

    private fun callbackFavorite(people: People) {
        viewModel.favoritePerson(people)
    }

    private fun setupRecycler() {
        with(binding.recyclerList) {
            adapter = adapterList
            layoutManager = LinearLayoutManager(context)
        }
    }
}