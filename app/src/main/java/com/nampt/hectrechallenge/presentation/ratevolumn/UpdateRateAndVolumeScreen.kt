package com.nampt.hectrechallenge.presentation.ratevolumn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nampt.hectrechallenge.R
import com.nampt.hectrechallenge.databinding.ScreenUpdateRateVolumeBinding
import com.nampt.hectrechallenge.presentation.adapters.RateVolumeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateRateAndVolumeScreen : Fragment(), RateVolumeAdapter.JobViewHolderListener {

    private lateinit var viewBinding: ScreenUpdateRateVolumeBinding
    private val rateVolumeViewModel by viewModel<RateVolumeViewModel>()
    private val rateVolumeAdapter = RateVolumeAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.screen_update_rate_volume, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        getData()
    }


    private fun initView() {
        viewBinding.rcvRateVolume.adapter = rateVolumeAdapter.apply {
            this.jobListener = this@UpdateRateAndVolumeScreen
        }
    }

    private fun initObserver() {
        rateVolumeViewModel.rateVolumeItemLiveData.observe(viewLifecycleOwner) {
            it?.let {
                rateVolumeAdapter.addDetailRows(rateVolumeViewModel.detailRows)
                rateVolumeAdapter.replaceData(it)
                rateVolumeAdapter.notifyItemRangeInserted(0, rateVolumeAdapter.itemCount)
            }
        }

        rateVolumeViewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is RateVolumeViewModel.RateVolumeUIState.RefreshJobState -> {
                    rateVolumeAdapter.replaceData(it.item)
                    rateVolumeAdapter.notifyItemRangeChanged(it.position, it.size + 1)
                }
            }
        }
    }

    private fun getData() {
        rateVolumeViewModel.getListJobAndRow()
    }


//    override fun onRateTypeClick(
//        rateType: RateTypeJson.RateType,
//        jobDetailId: String?,
//        jobId: String?
//    ) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onEditRateDone(rate: String, jobDetailId: String?, jobId: String?) {
//        TODO("Not yet implemented")
//    }
//
    override fun onAddMaxTreeClick(id: String?, position: Int) {
        rateVolumeViewModel.addMaxTreeForJob(id, position)
    }

    override fun onRowClick(rowId: String?, detailJobId: String?, jobId: String?) {
        rateVolumeViewModel.changeStaffRow(rowId, detailJobId, jobId)
    }

    override fun onApplyRate(rate: String?, jobId: String?) {
        rateVolumeViewModel.applyRateAll(rate, jobId)
    }

}