package com.nampt.hectrechallenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JobDetailJson(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("user")
    val user: UserJson? = null,

    @SerializedName("block")
    val block: BlockJson? = null,

    @SerializedName("orchard")
    val orchard: OrchardJson? = null,

    @SerializedName("salary")
    var salary: SalaryJson? = null,

    @SerializedName("ratetype")
    val ratetype: RateTypeJson? = null,

    @SerializedName("row")
    var row: List<RowJson>? = null
) : Parcelable {

}