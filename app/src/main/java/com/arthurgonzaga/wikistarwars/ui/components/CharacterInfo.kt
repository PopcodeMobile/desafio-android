package com.arthurgonzaga.wikistarwars.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.databinding.CharacterInfoLayoutBinding


class CharacterInfo @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private lateinit var titleStr: String
    private lateinit var subtitleStr: String

    lateinit var binding: CharacterInfoLayoutBinding



    init {
        context?.let {
            // Get the attribute values
            it.theme.obtainStyledAttributes(
                attrs, R.styleable.CharacterInfo,
                0, 0
            ).apply {
                try {
                    titleStr = this.getString(R.styleable.CharacterInfo_title) ?: ""
                    subtitleStr = this.getString(R.styleable.CharacterInfo_subtitle) ?: ""
                } finally {
                    recycle()
                }
            }

            binding = CharacterInfoLayoutBinding.inflate(LayoutInflater.from(it))

            binding.title.text = titleStr.replaceFirstChar(Char::uppercase)
            binding.subtitle.text = subtitleStr.replaceFirstChar(Char::uppercase)
            addView(binding.root)
        }
    }

    fun setTitle(text: String){
        binding.title.text = text
    }

    fun setSubtitle(text: String){
        binding.subtitle.text = text
    }

}