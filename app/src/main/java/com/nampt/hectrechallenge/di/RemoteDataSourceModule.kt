package com.nampt.hectrechallenge.di

import com.nampt.hectrechallenge.data.remote.TimeSheetService
import com.nampt.hectrechallenge.data.remote.TimeSheetServiceImpl
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory<TimeSheetService> { TimeSheetServiceImpl(get()) }
}