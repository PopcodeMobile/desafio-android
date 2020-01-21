package br.com.starwarswiki.views.person

import android.content.Context
import br.com.starwarswiki.R
import br.com.starwarswiki.utils.ThreadUtils
import br.com.starwarswiki.views.cardLayout
import br.com.starwarswiki.views.dslAddView

inline fun personSummaryView(crossinline func: PersonSummaryView.() -> Unit) {
    dslAddView(func)
}

class PersonSummaryView(context: Context) : BasePeopleView(context) {
    override fun view() {
        val person = person ?: return

        val height = person.height
        val gender = person.gender
        val mass = person.mass

        ThreadUtils.runOnMain {
            cardLayout {
                person(person)
                content(mapOf(
                    R.string.height to "$height cm",
                    R.string.gender to gender,
                    R.string.mass to "$mass kg"
                ))
            }
        }
    }

}