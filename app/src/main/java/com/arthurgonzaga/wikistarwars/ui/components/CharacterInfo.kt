package com.arthurgonzaga.wikistarwars.ui.components

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.databinding.CharacterInfoLayoutBinding


private const val TAG = "CharacterInfo"

class CharacterInfo @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private lateinit var titleStr: String
    private lateinit var subtitleStr: String

    lateinit var binding: CharacterInfoLayoutBinding

    private var shortAnimationDuration: Int = 0


    init {
        shortAnimationDuration = resources.getInteger(android.R.integer.config_mediumAnimTime)

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

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setSubtitle(text: String) {
        binding.subtitle.text = text
    }

    /**
     *  Show the view with a fading in animation
     */
    fun show() {
        Log.i(TAG, "showing $TAG")
        this.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        Log.d(TAG, "animation ended: ")
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationRepeat(p0: Animator?) {
                    }

                })
        }
    }

    /**
     *  Hide the view with a fading out animation
     */
    fun hide() {
        binding.root.apply {
            animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = View.GONE
                        }
                    }
                )
        }
    }
}