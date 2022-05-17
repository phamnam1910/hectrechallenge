package com.nampt.hectrechallenge.domain.usecase

import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import kotlinx.coroutines.flow.Flow

interface GetDetailRowUseCase {
    suspend fun getDetailRow(): Flow<DataResult<List<RowDetailJson>>>
}