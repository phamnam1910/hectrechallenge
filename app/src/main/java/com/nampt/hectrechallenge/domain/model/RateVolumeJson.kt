package com.nampt.hectrechallenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateVolumeJson(
    @SerializedName("job")
    var job: JobJson? = null,

    @SerializedName("jobDetail")
    var jobDetail: List<JobDetailJson>? = null
) : Parcelable {

}