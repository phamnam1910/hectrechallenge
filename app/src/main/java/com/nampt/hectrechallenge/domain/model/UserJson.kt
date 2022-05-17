package com.nampt.hectrechallenge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserJson( @SerializedName("id")
                     val id: String? = null,

    @SerializedName("firstName")
val firstName: String? = null,

@SerializedName("lastName")
val lastName: String? = null):Parcelable {

}