package com.nampt.hectrechallenge.data.remote.model

import com.google.gson.annotations.SerializedName

class RowDetailJson {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("totalTree")
    var totalTree: Int? = null

    @SerializedName("remainTree")
    var remainTree: Int? = null

    @SerializedName("place")
    var place: String? = null
}