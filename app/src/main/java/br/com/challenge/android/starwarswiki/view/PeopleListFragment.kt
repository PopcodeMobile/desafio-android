package br.com.challenge.android.starwarswiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import br.com.challenge.android.starwarswiki.R
import br.com.challenge.android.starwarswiki.view.adapter.TypeOfListFragmentTabAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PeopleListFragment(private val callback: PersonItemClickListener): Fragment() {
    private lateinit var typeOfListFragmentTabAdapter: TypeOfListFragmentTabAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        typeOfListFragmentTabAdapter = TypeOfListFragmentTabAdapter(this, callback)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = typeOfListFragmentTabAdapter

        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "People"
                }
                else -> {
                    tab.text = "Favorites"
                }
            }

        }.attach()
    }

}