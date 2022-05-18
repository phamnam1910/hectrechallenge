package com.nampt.hectrechallenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RowJson(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("treeDone")
    var treeDone: Int? = null
) : Parcelable {

}