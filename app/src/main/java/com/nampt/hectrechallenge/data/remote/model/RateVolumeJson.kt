package com.nampt.hectrechallenge.data.remote.model

import com.google.gson.annotations.SerializedName

class RateVolumeJson {
    @SerializedName("job")
    var job: JobJson? = null

    @SerializedName("jobDetail")
    var jobDetail: List<JobDetailJson>? = null
}