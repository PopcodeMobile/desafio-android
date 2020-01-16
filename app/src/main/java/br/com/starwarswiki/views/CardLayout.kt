package br.com.starwarswiki.views

import android.content.Context
import android.graphics.Typeface
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
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
import br.com.starwarswiki.R
import br.com.starwarswiki.actions.ActionCreator
import trikita.anvil.BaseDSL

object CardLayout {
    fun cardLayout(context: Context, title: String, isFavorite: Boolean, content: Map<Int, String>) {
        cardView {
            size(MATCH, WRAP)
            margin(dip(8))
            linearLayout {
                size(MATCH, WRAP)
                orientation(VERTICAL)
                padding(dip(8))

                cardTitle(title, isFavorite)
                content.forEach { renderCardLine(context.getString(it.key), it.value) }
            }
        }
    }

    private fun cardTitle(title: String, isFavorite: Boolean) {
        relativeLayout {
            size(MATCH, WRAP)
            orientation(HORIZONTAL)
            margin(0, 0, 0, dip(6))

            textView {
                text(title)
                BaseDSL.alignParentLeft()
                textSize(sip(24f))
                margin(0, 0, 0, dip(16))
                typeface(null, Typeface.BOLD)
            }

//            imageView {
//                size(50, 50)
//                BaseDSL.alignParentRight()
//                backgroundResource(getIconResource(isFavorite))
//                init {
//                    onClick {
//                        toggleFavorite(title, isFavorite)
//                    }
//                }
//            }
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

    private fun toggleFavorite(title: String, isFavorite: Boolean) {
        if (isFavorite)
            ActionCreator.removeFavorite(title)
        else
            ActionCreator.addFavorite(title)
    }
}