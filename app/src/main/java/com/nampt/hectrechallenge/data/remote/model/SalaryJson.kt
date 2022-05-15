package com.nampt.hectrechallenge.data.remote.model

import com.google.gson.annotations.SerializedName

class SalaryJson {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("rate")
    var rate: String? = null
}