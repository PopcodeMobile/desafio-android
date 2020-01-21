package br.com.starwarswiki.anvil

import android.content.Context
import android.widget.FrameLayout
import trikita.anvil.Anvil

abstract class FrameLayoutComponent(context: Context) : FrameLayout(context), Anvil.Renderable,
    RenderListener {

    private val layoutComponent = UiRenderHandler()

    override fun render() {
        layoutComponent.render(this)
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Anvil.mount(this, this)
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Anvil.unmount(this, false)
    }

    fun close() {
        Anvil.unmount(this, true)
    }
}