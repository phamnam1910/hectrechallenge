package com.nampt.hectrechallenge.di

import com.nampt.hectrechallenge.presentation.ratevolumn.RateVolumeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RateVolumeViewModel(get(), get()) }
}