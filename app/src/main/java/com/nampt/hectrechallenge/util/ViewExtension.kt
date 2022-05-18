package com.nampt.hectrechallenge.util

import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

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


class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(
                TAG,
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}