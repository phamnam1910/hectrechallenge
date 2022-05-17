package com.nampt.hectrechallenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrchardJson(  @SerializedName("id")
                    val id: String? = null,

    @SerializedName("name")
val name: String? = null):Parcelable {

}