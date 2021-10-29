package com.matheussilas97.starwarsapp.view.favorites

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.databinding.FragmentFavorityBinding
import com.matheussilas97.starwarsapp.utils.Constants
import com.matheussilas97.starwarsapp.view.charactersdetails.DetailsActivity

import android.content.DialogInterface
import com.matheussilas97.starwarsapp.database.model.FavoriteModel
import com.matheussilas97.starwarsapp.utils.BaseFragment


class FavorityFragment : BaseFragment() {

    private lateinit var binding: FragmentFavorityBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavorityBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]

        viewModel.list()
        observer()

        return binding.root
    }

    private fun observer() {
        viewModel.favoriteList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                buildList(it)
            }else{
                setNoResultAdapter(binding.recyclerFavorite, getString(R.string.no_favorites))
            }
        })
    }

    private fun buildList(it: List<FavoriteModel>) {
        val adapter = FavoriteAdapter()
        binding.recyclerFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavorite.adapter = adapter
        adapter.updateList(it)
        adapter.addOnItemClickListener(object : FavoriteAdapter.OnItemClickListener {
            override fun onClick(url: String) {
                val intent = Intent(requireContext(), DetailsActivity::class.java)
                intent.putExtra(Constants.URL, url)
                startActivity(intent)
            }

            override fun onDelete(id: String) {
                dialogDeleteFavorite(id)
            }

        })
    }

    private fun dialogDeleteFavorite(id: String) {
        val alertDialog: AlertDialog = AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(getString(R.string.favorite))
            .setMessage(R.string.unfavor_msg)
            .setPositiveButton(
                getString(R.string.disfavor),
                DialogInterface.OnClickListener { dialogInterface, i ->
                    deleteFavorite(id)
                    dialogInterface.dismiss()
                })
            .setNegativeButton(
                R.string.cancel,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
            .show()
    }

    private fun deleteFavorite(id: String) {
        viewModel.deleteFavorite(id)
        viewModel.list()
    }

    override fun onResume() {
        super.onResume()
        viewModel.list()
    }

}