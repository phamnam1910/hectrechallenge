package com.nampt.hectrechallenge

import android.app.Application
import com.nampt.hectrechallenge.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class TimeSheetApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TimeSheetApplication)
            modules(
                viewModelModule,
                useCasesModule,
                networkModule,
                localDataSourceModule,
                remoteDataSourceModule
            )
        }
    }
}