package com.albuquerque.starwarswiki.core.custom

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.albuquerque.starwarswiki.R

class WikiSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SearchView(context, attrs, defStyleAttr) {

    private val waitingTime = 600L
    private var countDownTimer: CountDownTimer? = null

    private val _searchTextInputView: EditText? = findViewById(androidx.appcompat.R.id.search_src_text)
    private val _searchCloseView: ImageView? = findViewById(androidx.appcompat.R.id.search_close_btn)

    private var _onQueryTextSubmit: (query: String) -> Unit = {}
    private var _onQueryTextChange: (query: String) -> Unit = {}

    init {
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        maxWidth = Int.MAX_VALUE

        _searchTextInputView?.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        _searchCloseView?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close))
    }

    private fun setupListeners() {

        setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    _onQueryTextSubmit(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                countDownTimer?.cancel()

                countDownTimer = object: CountDownTimer(waitingTime, 500L) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        newText?.let {
                            _onQueryTextChange(it)
                        }
                    }
                }

                countDownTimer?.start()
                return false
            }
        })

    }

    fun setOnQueryTextSubmit(onQueryTextSubmit: (query: String) -> Unit) {
        this._onQueryTextSubmit = onQueryTextSubmit
    }

    fun setOnQueryTextChange(onQueryTextChange: (query: String) -> Unit) {
        this._onQueryTextChange = onQueryTextChange
    }

}