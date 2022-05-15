package com.nampt.hectrechallenge.data.remote.model

import com.google.gson.annotations.SerializedName




class BlockJson {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null
}