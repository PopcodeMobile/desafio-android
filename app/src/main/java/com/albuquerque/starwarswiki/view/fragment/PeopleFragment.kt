package com.albuquerque.starwarswiki.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.albuquerque.starwarswiki.R
import com.albuquerque.starwarswiki.core.custom.WikiSearchView
import com.albuquerque.starwarswiki.extensions.setupToolbar
import com.albuquerque.starwarswiki.viewmodel.PeopleViewModel
import kotlinx.android.synthetic.main.fragment_people.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {

    private val peopleViewModel: PeopleViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.setupToolbar(toolbar, getString(R.string.toolbar_people))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.action_search_people)?.apply {
            setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    Toast.makeText(this@PeopleFragment.context, "onMenuItemActionExpand", Toast.LENGTH_LONG).show()
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    Toast.makeText(this@PeopleFragment.context, "onMenuItemActionCollapse", Toast.LENGTH_LONG).show()
                    return true
                }

            })
        }

        (menuItem?.actionView as? WikiSearchView)?.apply {

            setOnQueryTextChange {
                Toast.makeText(this@PeopleFragment.context, "setOnQueryTextChange", Toast.LENGTH_LONG).show()
            }

            setOnQueryTextSubmit {
                Toast.makeText(this@PeopleFragment.context, "setOnQueryTextSubmit", Toast.LENGTH_LONG).show()
            }

        }

        return super.onCreateOptionsMenu(menu, inflater)
    }

}
