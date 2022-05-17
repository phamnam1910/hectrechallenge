package com.nampt.hectrechallenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RateTypeJson(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("name")
var name: String? = null) : Parcelable {
    enum class RateType(val value: String) {
        PIECE_RATE("1"), WAGES("2")
    }

}