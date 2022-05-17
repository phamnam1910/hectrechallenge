package com.nampt.hectrechallenge.data.remote

import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.data.util.DataResult
import kotlinx.coroutines.flow.Flow

interface TimeSheetService {
    fun getListJob(): Flow<DataResult<List<RateVolumeJson>>>
    fun getDetailRows(): Flow<DataResult<List<RowDetailJson>>>
}