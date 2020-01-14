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
import trikita.anvil.DSL.MATCH
import trikita.anvil.DSL.linearLayout
import trikita.anvil.DSL.orientation
import trikita.anvil.DSL.size
import trikita.anvil.DSL.textView
import trikita.anvil.DSL.typeface
import trikita.anvil.DSL.weightSum
import trikita.anvil.cardview.v7.CardViewv7DSL.cardView

object CardLayout {
    fun cardLayout(context: Context, title: String, content: Map<Int, String>) {
        cardView {
            size(MATCH, WRAP)
            margin(dip(8))
            linearLayout {
                size(MATCH, WRAP)
                orientation(VERTICAL)
                padding(dip(8))

                cardTitle(title)
                content.forEach { renderCardLine(context.getString(it.key), it.value) }
            }
        }
    }

    fun cardTitle(title: String) {
        textView {
            text(title)
            textSize(sip(24f))
            margin(0, 0, 0, dip(16))
            typeface(null, Typeface.BOLD)
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
}