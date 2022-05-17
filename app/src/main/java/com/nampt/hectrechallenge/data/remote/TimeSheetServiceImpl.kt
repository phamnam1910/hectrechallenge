package com.nampt.hectrechallenge.data.remote

import com.nampt.hectrechallenge.data.remote.api.TimeSheetApi
import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.data.util.ErrorResponse
import com.nampt.hectrechallenge.data.util.defaultOffer
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
class TimeSheetServiceImpl(client: Retrofit) : TimeSheetService {

    private val timeSheetApi = client.create(TimeSheetApi::class.java)
    override fun getListJob(): Flow<DataResult<List<RateVolumeJson>>> {
        return callbackFlow {
            val callback =
                object : Callback<List<RateVolumeJson>> {
                    override fun onResponse(
                        call: Call<List<RateVolumeJson>>,
                        response: Response<List<RateVolumeJson>>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                defaultOffer(DataResult.Success(it))
                            } ?: kotlin.run {
                                defaultOffer(DataResult.Failure())
                            }
                        } else {
                            defaultOffer(DataResult.Failure())
                        }
                    }

                    override fun onFailure(call: Call<List<RateVolumeJson>>, t: Throwable) {
                        defaultOffer(DataResult.Failure(ErrorResponse(t.message)))
                    }
                }
            timeSheetApi.getRateAndVolume().enqueue(callback)
            awaitClose { callback }
        }
    }

    override fun getDetailRows(): Flow<DataResult<List<RowDetailJson>>> {
        return callbackFlow {

            val callback = object : Callback<List<RowDetailJson>> {
                override fun onResponse(
                    call: Call<List<RowDetailJson>>,
                    response: Response<List<RowDetailJson>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            defaultOffer(DataResult.Success(it))
                        } ?: kotlin.run {
                            defaultOffer(DataResult.Failure())
                        }
                    } else {
                        defaultOffer(DataResult.Failure())
                    }
                }

                override fun onFailure(call: Call<List<RowDetailJson>>, t: Throwable) {
                    defaultOffer(DataResult.Failure(ErrorResponse(t.message)))
                }
            }
            timeSheetApi.getDetailRow().enqueue(callback)

            awaitClose { callback }
        }
    }
}