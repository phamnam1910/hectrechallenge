package com.nampt.hectrechallenge

import android.app.Application
import android.content.Context
import com.nampt.hectrechallenge.di.dataSourceModule
import com.nampt.hectrechallenge.di.networkModule
import com.nampt.hectrechallenge.di.useCasesModule
import com.nampt.hectrechallenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class TimeSheetApplication : Application() {

    companion object {
        var context: TimeSheetApplication? = null
        fun applicationContext(): Context? {
            return context?.applicationContext
        }
    }

    init {
        context = this
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TimeSheetApplication)
            modules(
                viewModelModule,
                useCasesModule,
                networkModule,
                dataSourceModule
            )
        }
    }
}