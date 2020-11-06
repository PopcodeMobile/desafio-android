package br.com.challenge.android.starwarswiki.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.challenge.android.starwarswiki.view.PersonFragment
import br.com.challenge.android.starwarswiki.view.PersonItemClickListener

class TypeOfListFragmentTabAdapter(
    fragmentRelatedToAdapter: Fragment,
    private val callback: PersonItemClickListener): FragmentStateAdapter(fragmentRelatedToAdapter) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                return PersonFragment(callback)
            }
            else -> {
                return TODO() // favorites
            }
        }
    }

}