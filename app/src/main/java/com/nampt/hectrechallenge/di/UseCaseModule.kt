package com.nampt.hectrechallenge.di

import com.nampt.hectrechallenge.domain.usecase.GetDetailRowUseCase
import com.nampt.hectrechallenge.domain.usecase.GetListJobUseCase
import com.nampt.hectrechallenge.domain.usecase.impl.GetDetailRowUseCaseImpl
import com.nampt.hectrechallenge.domain.usecase.impl.GetDetailRowUseCaseImplFake
import com.nampt.hectrechallenge.domain.usecase.impl.GetListJobUseCaseImpl
import com.nampt.hectrechallenge.domain.usecase.impl.GetListJobUseCaseImplFake
import org.koin.dsl.module

val useCasesModule = module {
//    factory<GetListJobUseCase> { GetListJobUseCaseImpl(get()) }
//    factory<GetDetailRowUseCase> { GetDetailRowUseCaseImpl(get()) }

    //fake
    factory<GetListJobUseCase> { GetListJobUseCaseImplFake(get()) }
    factory<GetDetailRowUseCase> { GetDetailRowUseCaseImplFake(get()) }
}