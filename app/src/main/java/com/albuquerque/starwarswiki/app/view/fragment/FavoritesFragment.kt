package com.albuquerque.starwarswiki.app.view.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.albuquerque.starwarswiki.R
import com.albuquerque.starwarswiki.app.adapter.PeopleAdapter
import com.albuquerque.starwarswiki.app.extensions.*
import com.albuquerque.starwarswiki.app.viewmodel.FavoritesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private lateinit var peopleAdapter: PeopleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupView()

        subscribeUI()
    }

    private fun setupView() {
        peopleAdapter = PeopleAdapter(favoritesViewModel)
        (activity as? AppCompatActivity)?.setupToolbar(toolbar, getString(R.string.toolbar_favorites))
        rvFavorites.adapter = peopleAdapter

    }

    private fun subscribeUI() {
        with(favoritesViewModel) {

            favorites.observe(this@FavoritesFragment) {
                peopleAdapter.refresh(it)

                if(it.isEmpty())
                    emptyView.setVisible()
                else
                    emptyView.setGone()


            }

            onHandleFavorite.observe(this@FavoritesFragment) { pair ->
                pair.first?.let {
                    peopleAdapter.notifyItemChanged(it)

                    if (pair.second.isNotBlank() || pair.second.isNotEmpty())
                        Snackbar.make(rvFavorites, pair.second, Snackbar.LENGTH_LONG).success()

                } ?: kotlin.run {
                    Snackbar.make(rvFavorites, pair.second, Snackbar.LENGTH_LONG).error()
                }
            }

        }

    }

}
