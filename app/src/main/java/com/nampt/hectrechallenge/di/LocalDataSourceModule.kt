package com.nampt.hectrechallenge.di

import androidx.room.Room
import com.nampt.hectrechallenge.data.local.TimeSheetDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val localDataSourceModule = module {
    single {
        Room
            .databaseBuilder(androidContext(), TimeSheetDatabase::class.java, "timesheet_db")
            .build()
    }

}

