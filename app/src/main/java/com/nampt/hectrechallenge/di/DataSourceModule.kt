package com.nampt.hectrechallenge.di

import com.nampt.hectrechallenge.data.remote.TimeSheetService
import com.nampt.hectrechallenge.data.remote.TimeSheetServiceImpl
import com.nampt.hectrechallenge.data.repository.TimeSheetRepository
import com.nampt.hectrechallenge.data.repository.impl.TimeSheetRepositoryImpl
import org.koin.dsl.module

val dataSourceModule = module {
    factory<TimeSheetService> { TimeSheetServiceImpl(get()) }
    factory<TimeSheetRepository> { TimeSheetRepositoryImpl(get()) }

}
