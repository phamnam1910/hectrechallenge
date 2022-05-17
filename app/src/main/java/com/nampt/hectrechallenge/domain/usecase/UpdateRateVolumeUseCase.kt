package com.nampt.hectrechallenge.domain.usecase

import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import kotlinx.coroutines.flow.Flow

interface UpdateRateVolumeUseCase  {
    suspend fun updateRateVolume(rateVolumeRequest : RateVolumeJson) : Flow<DataResult<RateVolumeJson>>
}