package com.nampt.hectrechallenge.util

import android.os.SystemClock
import android.view.View

fun View.setOnDebounceClick(function: (View?) -> Unit) {
    setOnClickListener(object: DebounceableOnClickListener() {
        override fun onClickImpl(v: View?) {
            function.invoke(v)
        }
    })
}

abstract class DebounceableOnClickListener(time: Long = 300) : Debounceable(time), View.OnClickListener {
    final override fun onClick(v: View?) {
        if (!debounce()) {
            onClickImpl(v)
        }
    }

    abstract fun onClickImpl(v: View?)
}

open class Debounceable(private val debounceTime: Long) {
    private var lastClickedTimestamp: Long = 0

    protected fun debounce(): Boolean {
        val currentTimestamp = SystemClock.elapsedRealtime()
        val shouldDebounce = currentTimestamp - lastClickedTimestamp < debounceTime
        if (!shouldDebounce) {
            lastClickedTimestamp = currentTimestamp
        }
        return shouldDebounce
    }
}