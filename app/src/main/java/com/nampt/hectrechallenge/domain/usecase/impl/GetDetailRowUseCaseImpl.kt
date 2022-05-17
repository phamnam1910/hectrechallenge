package com.nampt.hectrechallenge.domain.usecase.impl

import com.google.gson.Gson
import com.nampt.hectrechallenge.TimeSheetApplication
import com.nampt.hectrechallenge.data.repository.TimeSheetRepository
import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.domain.usecase.GetDetailRowUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDetailRowUseCaseImpl(private val timeSheetRepository: TimeSheetRepository) : GetDetailRowUseCase {
    override suspend fun getDetailRow(): Flow<DataResult<List<RowDetailJson>>> {
        return timeSheetRepository.getDetailRows()
    }
}

class GetDetailRowUseCaseImplFake(private val timeSheetRepository: TimeSheetRepository) : GetDetailRowUseCase {
    override suspend fun getDetailRow(): Flow<DataResult<List<RowDetailJson>>> {
        return flow {
            emit(DataResult.Loading)
            delay(2000)
            val response =
                TimeSheetApplication.applicationContext()?.assets?.open("mockGetListRowApi.json")
                    ?.bufferedReader().use {
                        it?.readText()
                    }
            emit(DataResult.Success(Gson().fromJson(response, Array<RowDetailJson>::class.java).toList()))
        }
    }
}