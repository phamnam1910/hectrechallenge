package com.nampt.hectrechallenge.data.remote.api

import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import retrofit2.Call
import retrofit2.http.GET

internal interface TimeSheetApi {
    @GET("")
    fun getRateAndVolume(): Call<List<RateVolumeJson>>

    @GET("")
    fun getDetailRow(): Call<List<RowDetailJson>>
}