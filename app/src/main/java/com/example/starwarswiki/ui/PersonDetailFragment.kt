package com.example.starwarswiki.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.example.starwarswiki.R
import com.example.starwarswiki.database.PersonRoomDatabase
import com.example.starwarswiki.databinding.PersonDetailFragmentBinding
import com.example.starwarswiki.viewmodel.PersonDetailViewModel
import com.example.starwarswiki.viewmodel.PersonDetailViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PersonDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PersonDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = PersonDetailFragmentArgs.fromBundle(arguments!!)
        val binding = PersonDetailFragmentBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val dataSource = PersonRoomDatabase.getDatabase(application).personDao
        val viewModelFactory = PersonDetailViewModelFactory(args.url, dataSource)
        val PersonDetailViewModel =  ViewModelProviders
            .of(this, viewModelFactory).get(PersonDetailViewModel::class.java)
        binding.personDetailViewModel = PersonDetailViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}
