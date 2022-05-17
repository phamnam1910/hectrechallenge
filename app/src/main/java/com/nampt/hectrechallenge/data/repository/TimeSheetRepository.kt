package com.nampt.hectrechallenge.data.repository

import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import kotlinx.coroutines.flow.Flow

interface TimeSheetRepository {
    suspend fun getListJob() : Flow<DataResult<List<RateVolumeJson>>>
    suspend fun getDetailRows() : Flow<DataResult<List<RowDetailJson>>>
}