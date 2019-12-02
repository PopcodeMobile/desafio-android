package com.example.starwarswiki.ui

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.graphics.component3
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.starwarswiki.R
import com.example.starwarswiki.database.PersonRoomDatabase
import com.example.starwarswiki.databinding.PersonListFragmentBinding
import com.example.starwarswiki.network.FavoriteNetworkObject
import com.example.starwarswiki.repository.PersonListRepository
import com.example.starwarswiki.viewmodel.*
import kotlinx.android.synthetic.main.item_list_fragment.view.*
import kotlinx.android.synthetic.main.person_list_fragment.*
import org.json.JSONObject
import timber.log.Timber
import kotlin.math.absoluteValue

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
            FavoriteClickListener { person, position ->
                Timber.d("Position $position")
                viewModel.onFavoriteClicked(person, position)
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
        viewModel.favoriteResponse.observe(this, Observer {
            it?.let{
                if(it.isSuccessful){
                    Toast.makeText(context, "Response code: \n${it.code()}\nMessage: ${it.body()?.message}", Toast.LENGTH_SHORT).show()
//                    Timber.d("Response code: \n${it.code()}\nMessage: ${it.body()?.message}")
                }
                else{
                    val jsonObject = JSONObject(it.errorBody()?.string())
                    val errorObject = FavoriteNetworkObject(null, null, error = jsonObject.getString("error"), errorMessage = jsonObject.getString("error_message"))
                    Toast.makeText(context, "Response error: ${errorObject.error}\n${errorObject.errorMessage}!", Toast.LENGTH_SHORT).show()
                }
            viewModel.afterFavoriteResponse()
            }
        })

        viewModel.favoritePosition.observe(this, Observer {
            it?.let{
                adapter.notifyItemChanged(it)
                viewModel.afterFavorite()
            }
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
