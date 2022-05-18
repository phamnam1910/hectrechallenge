package com.nampt.hectrechallenge.domain.util

import com.nampt.hectrechallenge.domain.model.JobDetailJson
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.domain.model.RowJson
import com.nampt.hectrechallenge.presentation.adapters.RateVolumeItem
import com.nampt.hectrechallenge.presentation.adapters.RateVolumeItemViewType

fun RateVolumeJson.getRowIdParallel(): List<String> {
    val map = getActiveRowId()
    return map.filterValues { it > 1 }.keys.toList()
}

fun RateVolumeJson.getActiveRowId(): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    if (this.jobDetail.isNullOrEmpty()) return mapOf()
    this.jobDetail.let {
        for (staff in it) {
            if (staff.row == null) break
            val staffRow = staff.row ?: break
            for (row in staffRow) {
                if (row.id.isNullOrEmpty()) break
                val treeDone = row.treeDone
                if (treeDone != null) {
                    map[row.id] = map[row.id]?.plus(1) ?: 1
                }
            }
        }
    }

    return map
}

fun List<RowDetailJson>.getDetailRow(id: String?): RowDetailJson? {
    return this.firstOrNull { it.id == id }
}


fun List<JobDetailJson>.getStaffById(id: String?): JobDetailJson? {
    return this.firstOrNull { it.id == id }
}

fun List<RateVolumeJson>.getJobById(id: String?): RateVolumeJson? {
    return this.firstOrNull { it.job?.id == id }
}

fun List<RowJson>.getRowById(id: String?): RowJson? {
    return this.firstOrNull { it.id == id }
}

fun MutableList<RateVolumeJson>?.mockResetJob(job: RateVolumeJson?) {
    this?.let {
        it.set(it.indexOf(job), RateVolumeJson(job?.job, job?.jobDetail))
    }
}

fun List<RateVolumeItem>.firstIndexByJobId(id:String):Int {
    val firstBodyByJobId =
        this.firstOrNull { it.viewType == RateVolumeItemViewType.Body.value && (it as? RateVolumeItem.BodyItem)?.jobDetail?.job?.id == id }
   return this.indexOf(firstBodyByJobId)
}