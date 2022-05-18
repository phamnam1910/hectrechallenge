package com.nampt.hectrechallenge.presentation.adapters

import com.nampt.hectrechallenge.domain.model.JobDetailJson
import com.nampt.hectrechallenge.domain.model.JobJson
import com.nampt.hectrechallenge.domain.model.RateVolumeJson

sealed class RateVolumeItem(val viewType: Int) {
    data class HeaderItem(val JobJson: JobJson?) :
        RateVolumeItem(RateVolumeItemViewType.Header.value)

    data class BodyItem(val staff: JobDetailJson?, val jobDetail: RateVolumeJson) :
        RateVolumeItem(RateVolumeItemViewType.Body.value)

    object FooterItem : RateVolumeItem(RateVolumeItemViewType.Footer.value)
}

enum class RateVolumeItemViewType(val value: Int) {
    Header(0),
    Body(1),
    Footer(2)
}