package com.nampt.hectrechallenge.presentation.ratevolumn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.data.util.ErrorResponse
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.domain.usecase.GetDetailRowUseCase
import com.nampt.hectrechallenge.domain.usecase.GetListJobUseCase
import com.nampt.hectrechallenge.domain.util.getActiveRowId
import com.nampt.hectrechallenge.domain.util.getDetailRow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RateVolumeViewModel(
    private val getListJobUseCase: GetListJobUseCase,
    private val getDetailRowUseCase: GetDetailRowUseCase
) : ViewModel() {

    private var detailRows: List<RowDetailJson>? = null
    private var jobs: MutableList<RateVolumeJson>? = null
    val rateVolumeLiveData = MutableLiveData<LoadingUiModel>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getListJobAndRow() {
        viewModelScope.launch {
            val getListJob = getListJobUseCase.getListJob()
            val getDetailRow = getDetailRowUseCase.getDetailRow()
            merge(getListJob, getDetailRow)
                .onStart {
                    emit(DataResult.Loading)
                }.catch {
                    emit(DataResult.Failure())
                }.collect { it ->
                    when (it) {
                        is DataResult.Loading -> {

                        }
                        is DataResult.Failure -> {

                        }
                        is DataResult.Success -> {
                            if (it.data.any { it is RowDetailJson }) {
                                detailRows = it.data as List<RowDetailJson>
                            } else if (it.data.any { it is RateVolumeJson }) {
                                jobs = it.data as MutableList<RateVolumeJson>
                            }

                            if (detailRows != null && jobs != null) {
                                postRateVolumeData()
                            }
                        }
                    }
                }
        }
    }

    private fun postRateVolumeData() {
        rateVolumeLiveData.postValue(
            LoadingUiModel(
                detailRows = detailRows,
                jobs = jobs
            )
        )
    }

    fun addMaxTreeForJob(id: String?) {
        // Pair<ActiveRow, Number> : collect active row with number of appear
        //for staff -> check list row of staff contain active row , divide number

        //todo : check case remain tree is even number

        viewModelScope.launch {
            jobs?.firstOrNull { it.job?.id == id }?.apply {
                this.jobDetail?.let {
                    val mapOfActiveRow = this.getActiveRowId()
                    for (staff in it) {
                        if (staff.row == null) break
                        for (row in staff.row) {
                            val rowDetail = detailRows?.getDetailRow(row.id)
                            if (mapOfActiveRow.containsKey(row.id)) {
                                val shareTree =
                                    rowDetail?.remainTree?.div(mapOfActiveRow[row.id] ?: 1)
                                row.treeDone = row.treeDone?.plus(shareTree ?: 0)
                            }
                        }
                    }
                }
                jobs?.let {
                    it.set(it.indexOf(this), RateVolumeJson(this.job, this.jobDetail))
                }
                postRateVolumeData()
            }
        }
    }
//
//    fun updateStaffRateType(
//        rateType: RateTypeJson.RateType,
//        staff: JobDetailJson,
//        id: String?
//    ) {
//        when (rateType) {
//            RateTypeJson.RateType.PIECE_RATE -> {
//                staff.p
//            }
//            RateTypeJson.RateType.WAGES -> {
//
//            }
//        }
//
//        jobs?.firstOrNull { it.job?.id == id }.apply {
//            this?.jobDetail.
//        }
//    }

    data class LoadingUiModel(
        val loading: Boolean = false,
        val detailRows: List<RowDetailJson>? = null,
        val jobs: MutableList<RateVolumeJson>? = null,
        val error: ErrorResponse? = null
    )
}