package com.nampt.hectrechallenge.data.remote.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class OrchardJson {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null
}