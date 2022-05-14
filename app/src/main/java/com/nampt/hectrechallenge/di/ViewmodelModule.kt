package com.nampt.hectrechallenge.di

import com.nampt.hectrechallenge.presentation.ratevolumn.RateVolumnViewModel
import org.koin.dsl.module

val viewModelModule = module {
        factory { RateVolumnViewModel() }
}