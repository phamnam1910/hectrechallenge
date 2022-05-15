package com.nampt.hectrechallenge.data.remote.model

import com.google.gson.annotations.SerializedName

class JobDetailJson {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("user")
    var user: UserJson? = null

    @SerializedName("block")
    var block: BlockJson? = null

    @SerializedName("orchard")
    var orchard: OrchardJson? = null

    @SerializedName("salary")
    var salary: SalaryJson? = null

    @SerializedName("ratetype")
    var ratetype: RateTypeJson? = null

    @SerializedName("row")
    var row: List<RowJson>? = null
}