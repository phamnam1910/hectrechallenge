package com.nampt.hectrechallenge.presentation.ratevolumn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nampt.hectrechallenge.data.util.DataResult
import com.nampt.hectrechallenge.data.util.ErrorResponse
import com.nampt.hectrechallenge.domain.model.JobDetailJson
import com.nampt.hectrechallenge.domain.model.RateTypeJson
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.domain.usecase.GetDetailRowUseCase
import com.nampt.hectrechallenge.domain.usecase.GetListJobUseCase
import com.nampt.hectrechallenge.domain.util.*
import com.nampt.hectrechallenge.presentation.adapters.RateVolumeItem
import com.nampt.hectrechallenge.util.SingleLiveEvent
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

    var detailRows: List<RowDetailJson>? = null
    private var jobs: List<RateVolumeJson>? = null

    val rateVolumeItemLiveData = MutableLiveData<List<RateVolumeItem>>()
    val uiState = SingleLiveEvent<RateVolumeUIState>()

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
                                jobs = it.data as List<RateVolumeJson>
                            }

                            if (detailRows != null && jobs != null) {
                                generateUIModel()
                            }
                        }
                    }
                }
        }
    }

    fun addMaxTreeForJob(id: String?, position: Int) {
        // Pair<ActiveRow, Number> : collect active row with number of appear
        //for staff -> check list row of staff contain active row , divide number

        //todo : check case remain tree is even number

        viewModelScope.launch {
            jobs?.firstOrNull { it.job?.id == id }?.apply {
                this.jobDetail?.let {
                    val mapOfActiveRow = this.getActiveRowId()
                    for (staff in it) {
                        if (staff.row == null) break
                        val staffRow = staff.row ?: break
                        for (row in staffRow) {
                            val rowDetail = detailRows?.getDetailRow(row.id)
                            if (mapOfActiveRow.containsKey(row.id) && row.treeDone != null) {
                                val shareTree =
                                    rowDetail?.remainTree?.div(mapOfActiveRow[row.id] ?: 1)
                                row.treeDone = row.treeDone?.plus(shareTree ?: 0)
                            }
                        }
                    }
                    uiState.postValue(
                        RateVolumeUIState.RefreshJobState(
                            getUIItem(),
                            position,
                            it.size
                        )
                    )
                }
            }
        }
    }

    private fun generateUIModel() {
        val listItem = getUIItem()
        rateVolumeItemLiveData.postValue(listItem)
    }

    private fun getUIItem(): MutableList<RateVolumeItem> {
        val listItem = mutableListOf<RateVolumeItem>()
        jobs?.let {
            for (job in it) {
                listItem.add(RateVolumeItem.HeaderItem(job.job))
                job.jobDetail?.let {
                    for (staff in it) {
                        listItem.add(
                            RateVolumeItem.BodyItem(
                                staff,
                                job,
                                isShowDivider = it.indexOf(staff) != 0
                            )
                        )
                    }
                }
                listItem.add(RateVolumeItem.FooterItem)
            }
        }
        return listItem
    }

    fun changeStaffRow(rowId: String?, jobDetailId: String?, jobId: String?) {
        viewModelScope.launch {
            jobs?.getJobById(jobId).apply {
                if (this?.jobDetail == null) return@launch
                this.jobDetail.getStaffById(jobDetailId)?.apply {
                    this.row?.getRowById(rowId)?.apply {
                        if (this.treeDone != null) {
                            this.treeDone = null
                        } else {
                            this.treeDone = 0
                        }
                    }
                }
                refreshByJob(jobId, this.jobDetail)
            }
        }
    }

    fun applyRateAll(rate: String?, jobId: String?) {
        viewModelScope.launch {
            jobs?.getJobById(jobId).apply {
                if (this?.jobDetail == null) return@launch
                for (staff in this.jobDetail) {
                    if (staff.ratetype?.id == RateTypeJson.RateType.PIECE_RATE.value) {
                        staff.salary?.rate = rate?.toIntOrNull()
                    }
                }
                refreshByJob(jobId, this.jobDetail)
            }
        }
    }

    private fun refreshByJob(
        jobId: String?,
        jobDetail: List<JobDetailJson>
    ) {
        val uiItem = getUIItem()
        val firstPosition = uiItem.firstIndexByJobId(jobId ?: "")
        uiState.postValue(
            RateVolumeUIState.RefreshJobState(
                getUIItem(),
                firstPosition,
                jobDetail.size
            )
        )
    }

    data class LoadingUiModel(
        val loading: Boolean = false,
        val detailRows: List<RowDetailJson>? = null,
        val jobs: List<RateVolumeJson>? = null,
        val error: ErrorResponse? = null
    )

    sealed class RateVolumeUIState {
        data class RefreshJobState(
            val item: List<RateVolumeItem>,
            val position: Int,
            val size: Int
        ) :
            RateVolumeUIState()
    }
}