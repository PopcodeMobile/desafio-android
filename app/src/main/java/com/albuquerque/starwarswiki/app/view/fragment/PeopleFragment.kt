package com.albuquerque.starwarswiki.app.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.starwarswiki.R
import com.albuquerque.starwarswiki.app.adapter.PeopleAdapter
import com.albuquerque.starwarswiki.app.extensions.*
import com.albuquerque.starwarswiki.app.view.activity.PersonDetailActivity
import com.albuquerque.starwarswiki.app.view.activity.PersonDetailActivity.Companion.PERSON
import com.albuquerque.starwarswiki.app.viewmodel.PeopleViewModel
import com.albuquerque.starwarswiki.app.custom.WikiSearchView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_people.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PeopleFragment : Fragment() {

    private val peopleViewModel: PeopleViewModel by viewModel()
    private lateinit var peopleAdapter: PeopleAdapter
    private var isSearching = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
        subscribeUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.action_search_people)?.apply {
            setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    peopleViewModel.clearPeople()
                    emptyViewSearch.setVisible()
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    isSearching = false
                    peopleViewModel.getPeople()
                    emptyViewSearch.setGone()
                    return true
                }

            })
        }

        (menuItem?.actionView as? WikiSearchView)?.apply {

            setOnQueryTextSubmit {
                isSearching = true
                peopleViewModel.search(it)
            }

            findViewById<ImageView>(R.id.search_close_btn).setOnClickListener {
                this.setQuery("", false)
                isSearching = false
                peopleViewModel.clearPeople()
            }

        }

        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupView() {
        peopleAdapter = PeopleAdapter(peopleViewModel)
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.setupToolbar(toolbar, getString(R.string.toolbar_people))
        rvPeople.adapter = peopleAdapter

        rvPeople.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    peopleViewModel.handleNextPage()
                }
            }
        })

    }

    private fun subscribeUI() {

        with(peopleViewModel) {

            people.observe(this@PeopleFragment) {
                peopleAdapter.refresh(it)
                setLastPage(it.size)

                if (it.isNotEmpty()) {
                    emptyViewSearch.setGone()
                } else if(it.isEmpty() && isSearching) {
                    emptyViewSearch.setVisible()
                    setupSearchingEmptyView()
                } else if(it.isEmpty() && !isSearching) {
                    emptyViewSearch.setVisible()
                    setupDefaultEmptyView()
                }

            }

            tryFavoriteAgainList.observe(this@PeopleFragment) { list ->

                if(isTryAgainEnabled) {
                    list.forEach {
                        handleFavorite(it, it.tryAgainPosition!!)
                    }

                    isTryAgainEnabled = false
                    clearTryAgainList()
                }
            }

            onError.observe(this@PeopleFragment) {

                this@PeopleFragment.context?.let { context ->
                    Snackbar.make(rvPeople, it.invoke(context), Snackbar.LENGTH_LONG).error()
                }

            }

            onHandleFavorite.observe(this@PeopleFragment) { pair ->

                pair.first?.let {

                    if (pair.second.isNotBlank() || pair.second.isNotEmpty())
                        Snackbar.make(rvPeople, pair.second, Snackbar.LENGTH_LONG).success()

                } ?: kotlin.run {
                    Snackbar.make(rvPeople, pair.second, Snackbar.LENGTH_LONG).error()
                }


            }

            onHandleClick.observe(this@PeopleFragment) { person->
                startActivity(
                    Intent(this@PeopleFragment.activity, PersonDetailActivity::class.java)
                        .putExtras(Bundle().apply { putSerializable(PERSON, person) })
                )
            }

            onStartLoading.observe(this@PeopleFragment) {
                emptyViewSearch.setGone()
                progress.setVisible()
            }

            onFinishLoading.observe(this@PeopleFragment) {
                progress.setGone()
                rvPeople.setVisible()
            }

            config.observe(this@PeopleFragment) { config ->
                config?.let {
                    setCount(it.count)
                }
            }

        }

    }

    private fun setupSearchingEmptyView() {
        imageEmptyView.setImageResource(R.drawable.ic_empty_list)
        messageEmptyView.text = getString(R.string.search_message_empty)

    }

    private fun setupDefaultEmptyView() {
        imageEmptyView.setImageResource(R.drawable.ic_star_wars)
        messageEmptyView.text = getString(R.string.search_message_start)
    }

}
