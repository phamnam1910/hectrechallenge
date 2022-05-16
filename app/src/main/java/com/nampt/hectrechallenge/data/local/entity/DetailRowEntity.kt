package com.nampt.hectrechallenge.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nampt.hectrechallenge.data.util.Constants

@Entity(tableName = Constants.DetailRowTable)
class DetailRowEntity {
    @PrimaryKey
    var rowId: String = ""
    var name: String? = null
    var totalTree: Int? = null
    var remainTree: Int? = null
    var place: String? = null
}