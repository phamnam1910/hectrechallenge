package com.nampt.hectrechallenge.data.remote.model


import com.google.gson.annotations.SerializedName

class UserJson {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("firstName")
    var firstName: String? = null

    @SerializedName("lastName")
    var lastName: String? = null
}