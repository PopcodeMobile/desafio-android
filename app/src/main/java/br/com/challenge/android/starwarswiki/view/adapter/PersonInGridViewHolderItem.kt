package br.com.challenge.android.starwarswiki.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.challenge.android.starwarswiki.R

class PersonInGridViewHolderItem(view: View): RecyclerView.ViewHolder(view) {
    var personNameInItem: TextView = view.findViewById(R.id.personNameTextView)
    var personHeightInItem: TextView = view.findViewById(R.id.personHeightTextView)
    var personGenderInItem: TextView = view.findViewById(R.id.personGenderTextView)
    var personMassInItem: TextView = view.findViewById(R.id.personMassTextView)
}