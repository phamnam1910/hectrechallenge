package com.nampt.hectrechallenge.domain.util

import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.domain.model.RowJson

fun RateVolumeJson.getRowIdParallel(): List<String> {
    val map = getActiveRowId()
    return map.filterValues { it > 1 }.keys.toList()
}

fun RateVolumeJson.getActiveRowId() : Map<String,Int> {
    val map = mutableMapOf<String, Int>()
    if (this.jobDetail.isNullOrEmpty()) return mapOf()
    this.jobDetail?.let {
        for (staff in it) {
            if (staff.row == null) break
            for (row in staff.row) {
                if (row.id.isNullOrEmpty()) break
                val treeDone = row.treeDone
                if (treeDone != null && treeDone > 0) {
                    map[row.id] = map[row.id]?.plus(1) ?: 1
                }
            }
        }
    }

    return map
}

fun List<RowDetailJson>.getDetailRow(id : String?) : RowDetailJson? {
    return this.firstOrNull { it.id == id }
}