package com.example.starwarswiki.ui

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.starwarswiki.R
import com.example.starwarswiki.database.PersonRoomDatabase
import com.example.starwarswiki.databinding.PersonListFragmentBinding
import com.example.starwarswiki.repository.PersonListRepository
import com.example.starwarswiki.viewmodel.*
import kotlinx.android.synthetic.main.item_list_fragment.view.*
import kotlinx.android.synthetic.main.person_list_fragment.*
import timber.log.Timber

class PersonListFragment : Fragment() {
    private val viewModel: PersonListViewModel by lazy{
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, PersonListViewModelFactory(
            PersonRoomDatabase.getDatabase(activity).personDao,
            activity.application)
        )
            .get(PersonListViewModel::class.java)
    }
    private lateinit var adapter: PersonListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = PersonListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        adapter = PersonListAdapter(
            PersonClickListener { id ->
                viewModel.onPersonClicked(id)
            },
            FavoriteClickListener { person, view ->
                viewModel.onFavoriteClicked(person, view)
            }
            )
        viewModel.detailPerson.observe(this, Observer {
            it?.let{
                this.findNavController()
                    .navigate(PersonListFragmentDirections
                        .actionPersonListFragmentToPersonDetailFragment(it))
                viewModel.onPersonDetailed()
            }
        })
        viewModel.favoriteView.observe(this, Observer {
//            Toast.makeText(context, "Item ${viewModel.favoriteId.value?.name} favoritado !", Toast.LENGTH_SHORT).show()
//            it?.let{
//                it.favorite.setImageResource(R.drawable.ic_star)
//            }
        })

        binding.personList.adapter = adapter
        viewModel.personList.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.personSearch.observe(this, Observer {
            it?.let{
                Timber.d("Count items: ${it.size}")
                adapter.submitList(it)
            }
        })

        viewModel.eventNetworkError.observe(this, Observer {
            if(it==true){
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT)
                    .show()
                viewModel.onNetworkErrorShown()
            }
        })
        viewModel.showSnackbarEvent.observe(this, Observer {
            if(it==true){
                Toast.makeText(
                    context,
                    "Database cleared !",
                    Toast.LENGTH_SHORT
                ).show()
            }
            viewModel.doneShowingSnackbar()
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bar, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem!!.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(context, "$query", Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{
                    viewModel.onInputText(it)
                }
                return false
            }
        })
    }
}
