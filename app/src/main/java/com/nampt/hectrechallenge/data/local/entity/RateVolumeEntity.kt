package com.nampt.hectrechallenge.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nampt.hectrechallenge.data.util.Constants

@Entity(tableName = Constants.RateVolumeTable)
class RateVolumeEntity {
    @PrimaryKey
    var jobId: String = ""
    var jobName: String? = null
    var jobDetail: String? = null
}