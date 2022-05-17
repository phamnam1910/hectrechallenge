package com.nampt.hectrechallenge.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nampt.hectrechallenge.R
import com.nampt.hectrechallenge.databinding.ItemJobBinding
import com.nampt.hectrechallenge.domain.model.RateVolumeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.util.setOnDebounceClick

class JobAdapter : ListAdapter<RateVolumeJson, JobAdapter.JobViewHolder>(JobsDiffUtil()) {


    interface JobViewHolderListener {
        fun onAddMaxTreeClick(id: String?)
    }
    private var detailRows: List<RowDetailJson> = mutableListOf()
    private var jobs: List<RateVolumeJson> = mutableListOf()
    var staffListener: StaffAdapter.StaffViewHolderListener? = null
    var jobListener: JobViewHolderListener? = null

    fun replaceData(jobs: List<RateVolumeJson>, detailRows: List<RowDetailJson>) {
        this.detailRows = detailRows
        this.jobs = jobs
        submitList(jobs.toList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemJobBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.item_job, parent, false
        )
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bindView()
    }

    inner class JobViewHolder(private val binding: ItemJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView() {
            val job = jobs[adapterPosition]
            binding.tvJob.text = job.job?.name
            binding.btnAddMaxTree.apply {
//                visibility = if (job.jobDetail != null && job.jobDetail.size > 1) {
//                    View.VISIBLE
//                } else {
//                    View.INVISIBLE
//                }
                setOnDebounceClick {
                    jobListener?.onAddMaxTreeClick(job.job?.id)
                }
            }
            binding.rcvStaff.adapter = StaffAdapter().apply {
                this.staffListener = this@JobAdapter.staffListener
                this.replaceData(job, detailRows)
            }
        }
    }

    class JobsDiffUtil : DiffUtil.ItemCallback<RateVolumeJson>() {
        override fun areItemsTheSame(oldItem: RateVolumeJson, newItem: RateVolumeJson): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: RateVolumeJson, newItem: RateVolumeJson): Boolean {
            return oldItem.jobDetail == newItem.jobDetail && oldItem.job == newItem.job
        }
    }
}