package com.nampt.hectrechallenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateVolumeJson(
    @SerializedName("job")
    val job: JobJson? = null,

    @SerializedName("jobDetail")
    val jobDetail: List<JobDetailJson>? = null
) : Parcelable {

}