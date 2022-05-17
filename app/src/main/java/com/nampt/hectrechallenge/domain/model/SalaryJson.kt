package com.nampt.hectrechallenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SalaryJson(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("rate")
    var rate: Number? = null
) : Parcelable {

}
