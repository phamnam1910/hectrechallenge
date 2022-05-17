package com.nampt.hectrechallenge.domain.model

import com.google.gson.annotations.SerializedName

class RowDetailJson {
    @SerializedName("id")
    val id: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("totalTree")
    val totalTree: Int? = null

    @SerializedName("remainTree")
    val remainTree: Int? = null

    @SerializedName("place")
    val place: String? = null

    @Transient
    var isParallel : Boolean = false
}