package com.nampt.hectrechallenge.data.remote.model

import com.google.gson.annotations.SerializedName


class RowJson {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("treeDone")
    var treeDone: Int? = null
}