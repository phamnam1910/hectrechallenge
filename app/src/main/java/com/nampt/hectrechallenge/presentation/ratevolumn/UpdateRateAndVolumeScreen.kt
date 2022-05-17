package com.nampt.hectrechallenge.presentation.ratevolumn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nampt.hectrechallenge.R
import com.nampt.hectrechallenge.databinding.ScreenUpdateRateVolumeBinding
import com.nampt.hectrechallenge.domain.model.RateTypeJson
import com.nampt.hectrechallenge.presentation.adapters.JobAdapter
import com.nampt.hectrechallenge.presentation.adapters.StaffAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateRateAndVolumeScreen : Fragment(), JobAdapter.JobViewHolderListener,
    StaffAdapter.StaffViewHolderListener {

    private lateinit var viewBinding: ScreenUpdateRateVolumeBinding
    private val rateVolumeViewModel by viewModel<RateVolumeViewModel>()
    private val jobAdapter = JobAdapter()

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
        viewBinding.rcvRateVolume.itemAnimator = null
        viewBinding.rcvRateVolume.setHasFixedSize(true)
        viewBinding.rcvRateVolume.adapter = jobAdapter.apply {
            jobListener = this@UpdateRateAndVolumeScreen
            staffListener = this@UpdateRateAndVolumeScreen
        }
    }

    private fun initObserver() {
        rateVolumeViewModel.rateVolumeLiveData.observe(viewLifecycleOwner) {
            if (it.jobs != null && it.detailRows != null) {
                jobAdapter.replaceData(it.jobs, it.detailRows)
            }
        }
    }

    private fun getData() {
        rateVolumeViewModel.getListJobAndRow()
    }


    override fun onRateTypeClick(
        rateType: RateTypeJson.RateType,
        jobDetailId: String?,
        jobId: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun onEditRateDone(rate: String, jobDetailId: String?, jobId: String?) {
        TODO("Not yet implemented")
    }

    override fun onApplyRateToAll(rate: String, jobId: String?) {
        TODO("Not yet implemented")
    }

    override fun onAddMaxTreeClick(id: String?) {
        rateVolumeViewModel.addMaxTreeForJob(id)
    }

}