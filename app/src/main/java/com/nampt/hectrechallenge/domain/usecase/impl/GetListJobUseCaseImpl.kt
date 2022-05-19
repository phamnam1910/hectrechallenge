package com.nampt.hectrechallenge.domain.usecase.impl

import com.google.gson.Gson
import com.nampt.hectrechallenge.TimeSheetApplication
import com.nampt.hectrechallenge.data.repository.TimeSheetRepository
import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.usecase.GetListJobUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetListJobUseCaseImpl(private val timeSheetRepository: TimeSheetRepository) :
    GetListJobUseCase {
    override fun getListJob(): Flow<DataResult<List<RateVolumeJson>>> {
        return timeSheetRepository.getListJob()
    }
}

class GetListJobUseCaseImplFake(private val timeSheetRepository: TimeSheetRepository) :
    GetListJobUseCase {
    override  fun getListJob(): Flow<DataResult<List<RateVolumeJson>>> {
        return flow {
            emit(DataResult.Loading)
            delay(2000)
            val response =
                TimeSheetApplication.applicationContext()?.assets?.open("mockGetListJobAPI.json")
                    ?.bufferedReader().use {
                    it?.readText()
                }
            emit(DataResult.Success(Gson().fromJson(response, Array<RateVolumeJson>::class.java).toList()))
        }

    }
}