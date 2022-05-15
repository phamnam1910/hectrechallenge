package com.nampt.hectrechallenge.presentation.ratevolumn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nampt.hectrechallenge.R
import com.nampt.hectrechallenge.databinding.RateVolumnScreenBinding
import com.nampt.hectrechallenge.presentation.adapters.JobAdapter

class UpdateRateAndVolumnScreen : Fragment() {

    private lateinit var viewBinding : RateVolumnScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.screen_update_rate_volume,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewBinding.rcvRateVolume.setHasFixedSize(true)
        viewBinding.rcvRateVolume.adapter = JobAdapter()
    }
}