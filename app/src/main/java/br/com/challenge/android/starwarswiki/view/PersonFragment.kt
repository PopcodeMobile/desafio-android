package br.com.challenge.android.starwarswiki.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.challenge.android.starwarswiki.R
import br.com.challenge.android.starwarswiki.view.adapter.PeopleGridViewAdapter
import br.com.challenge.android.starwarswiki.viewmodel.PeopleViewModel
import br.com.challenge.android.starwarswiki.viewmodel.ViewModelFactory

class PersonFragment(private val callback: PersonItemClickListener) : Fragment() {
    private lateinit var gridViewAdapter: PeopleGridViewAdapter
    private lateinit var personViewModel: PeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val moviesGridView = view.findViewById<GridView>(R.id.peopleGridView)
        val inflater = LayoutInflater.from(view.context)
        gridViewAdapter = PeopleGridViewAdapter(inflater, ArrayList(), callback)

        setupViewModel(view.context)
        setupObserver()

        moviesGridView.emptyView = view.findViewById(R.id.emptyGridTextView)
        moviesGridView.adapter = gridViewAdapter
    }

    private fun setupViewModel(viewContext: Context){
        personViewModel = ViewModelProvider(
            this,
            ViewModelFactory(viewContext)
        ).get(PeopleViewModel::class.java)
    }

    private fun setupObserver(){
        personViewModel.peopleLiveData.observe(this, {
//            when(it.status) {
//                Status.LOADING -> {}
//                Status.SUCCESS -> {
//                    gridViewAdapter.refreshData(it.data!!)
//                }
//                Status.ERROR -> {
//                    if(it.message.equals(CheckNetwork.ERROR_INTERNET_NOT_AVAILABLE)) {
//                        // TODO() start listening a callback to network connection or using another approach, acquire
//                    }
//                }
//            }
        })
    }

}