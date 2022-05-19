package com.nampt.hectrechallenge.domain.usecase

import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import kotlinx.coroutines.flow.Flow

interface GetListJobUseCase {
     fun getListJob() : Flow<DataResult<List<RateVolumeJson>>>
}