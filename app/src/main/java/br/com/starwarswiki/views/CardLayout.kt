package br.com.starwarswiki.views

import android.content.Context
import android.graphics.Typeface
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import br.com.starwarswiki.R
import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.anvil.ReactiveFrameComponent
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.dip
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.sip
import trikita.anvil.BaseDSL.text
import trikita.anvil.BaseDSL.textSize
import trikita.anvil.BaseDSL.weight
import trikita.anvil.DSL.*
import trikita.anvil.cardview.v7.CardViewv7DSL.cardView

inline fun cardLayout(crossinline func: CardLayout.() -> Unit) {
    dslAddView(func)
}

class CardLayout(context: Context) : ReactiveFrameComponent(context) {

    private var content = mapOf<Int, String>()
    private var person: Person? = null

    override fun view() {
        val person = person ?: return
        cardView {
            size(MATCH, WRAP)
            margin(dip(8))
            linearLayout {
                size(MATCH, WRAP)
                orientation(VERTICAL)
                padding(dip(8))

                cardTitle(person)
                content.forEach { renderCardLine(context.getString(it.key), it.value) }
            }
        }
    }

    private fun cardTitle(person: Person) {
        relativeLayout {
            size(MATCH, WRAP)
            orientation(HORIZONTAL)
            margin(0, 0, 0, dip(6))

            textView {
                text(person.name)
                alignParentLeft()
                textSize(sip(24f))
                margin(0, 0, 0, dip(16))
                typeface(null, Typeface.BOLD)
            }

            imageView {
                size(50, 50)
                alignParentRight()
                backgroundResource(getIconResource(person.isFavorite))
                onClick {
                    toggleFavorite(person)
                }
            }
        }
    }

    private fun renderCardLine(title: String, content: String) {
        linearLayout {
            size(MATCH, WRAP)
            orientation(HORIZONTAL)
            margin(0, 0, 0, dip(6))
            weightSum(4f)

            renderTextView(title)
            renderTextView(content, true)
        }
    }

    private fun renderTextView(text: String, isBold: Boolean = false) {
        textView {
            size(0, WRAP)
            weight(2f)
            text(text)
            textSize(sip(14f))
            if (isBold) typeface(null, Typeface.BOLD)
        }
    }

    private fun getIconResource(isFavorite: Boolean): Int {
        return if (isFavorite)
            R.drawable.baseline_favorite_24
        else
            R.drawable.baseline_favorite_border_24
    }

    private fun toggleFavorite(person: Person) {
        if (person.isFavorite)
            ActionCreator.removeFavorite(person.name)
        else
            ActionCreator.addFavorite(person.name)
    }

    fun person(person: Person) {
        this.person = person
        render()
    }

    fun content(content: Map<Int, String>) {
        this.content = content
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        val person = person ?: return false
        return newState.people[person.name] != oldState.people[person.name]
    }

    override fun onChanged(state: AppState) {
        val person = state.people[person?.name] ?: return
        person(person)
    }
}