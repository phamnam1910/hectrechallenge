package com.nampt.hectrechallenge.data.remote.model

import com.google.gson.annotations.SerializedName


class JobJson {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null
}