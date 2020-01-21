package br.com.starwarswiki.utils

import android.os.Handler
import android.os.Looper

object ThreadUtils {
    private val isOnMain: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()

    val id: Int
        get() = android.os.Process.myTid()

    fun runOnMain(runnable: Runnable) {
        if (isOnMain)
            runnable.run()
        else
            Handler(Looper.getMainLooper()).post(runnable)
    }

    fun runOnMain(callback: () -> Unit) {
        runOnMain(Runnable(callback))
    }
}