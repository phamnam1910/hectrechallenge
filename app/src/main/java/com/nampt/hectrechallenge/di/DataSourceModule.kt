package com.nampt.hectrechallenge.di

import android.app.Application
import androidx.room.Room
import com.nampt.hectrechallenge.data.local.TimeSheetDao
import com.nampt.hectrechallenge.data.local.TimeSheetDatabase
import com.nampt.hectrechallenge.data.remote.TimeSheetService
import com.nampt.hectrechallenge.data.remote.TimeSheetServiceImpl
import com.nampt.hectrechallenge.data.repository.TimeSheetRepository
import com.nampt.hectrechallenge.data.repository.impl.TimeSheetRepositoryImpl
import com.nampt.hectrechallenge.data.util.Constants
import org.koin.dsl.module

val dataSourceModule = module {
    single {
        createTimeSheetDatabase(get())
    }
    single { createTimeSheetDao(get()) }

    factory<TimeSheetService> { TimeSheetServiceImpl(get()) }
    factory<TimeSheetRepository> { TimeSheetRepositoryImpl(get(), get()) }

}

fun createTimeSheetDatabase(context: Application): TimeSheetDatabase {
    return Room
        .databaseBuilder(context, TimeSheetDatabase::class.java, Constants.TimeSheetDB)
        .build()
}

fun createTimeSheetDao(appDb: TimeSheetDatabase): TimeSheetDao {
    return appDb.timeSheetDao
}