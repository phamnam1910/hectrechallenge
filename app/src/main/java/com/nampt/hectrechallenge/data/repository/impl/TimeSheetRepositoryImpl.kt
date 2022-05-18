package com.nampt.hectrechallenge.data.repository.impl

import com.nampt.hectrechallenge.data.remote.TimeSheetService
import com.nampt.hectrechallenge.data.repository.TimeSheetRepository
import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import kotlinx.coroutines.flow.Flow

internal class TimeSheetRepositoryImpl(
    val timeSheetService: TimeSheetService
) : TimeSheetRepository {
    override suspend fun getListJob(): Flow<DataResult<List<RateVolumeJson>>> {
        return timeSheetService.getListJob()
    }

    override suspend fun getDetailRows(): Flow<DataResult<List<RowDetailJson>>> {
        return timeSheetService.getDetailRows()
    }

}