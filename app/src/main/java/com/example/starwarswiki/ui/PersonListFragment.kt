package com.example.starwarswiki.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.starwarswiki.R
import com.example.starwarswiki.databinding.PersonListFragmentBinding
import com.example.starwarswiki.viewmodel.PersonListAdapter
import com.example.starwarswiki.viewmodel.PersonListViewModel
import com.example.starwarswiki.viewmodel.PersonListViewModelFactory

class PersonListFragment : Fragment() {

    private val viewModel: PersonListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, PersonListViewModelFactory(activity.application))
            .get(PersonListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = PersonListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = PersonListAdapter()
        binding.personList.adapter = adapter
        viewModel.personList.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        viewModel.eventNetworkError.observe(this, Observer {
            if(it==true)
                onNetworkError()
        })

        return binding.root
    }

    private fun onNetworkError(){
        if(!viewModel.eventNetworkError.value!!){
            Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT)
                .show()
            viewModel.onNetworkErrorShown()
        }
    }
}
