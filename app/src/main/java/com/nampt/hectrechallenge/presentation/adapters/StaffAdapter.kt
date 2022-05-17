package com.nampt.hectrechallenge.presentation.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nampt.hectrechallenge.R
import com.nampt.hectrechallenge.databinding.ItemStaffBinding
import com.nampt.hectrechallenge.databinding.ViewEditTreesNumberBinding
import com.nampt.hectrechallenge.databinding.ViewRowBinding
import com.nampt.hectrechallenge.domain.model.*
import com.nampt.hectrechallenge.domain.util.getDetailRow
import com.nampt.hectrechallenge.domain.util.getRowIdParallel
import com.nampt.hectrechallenge.util.setOnDebounceClick

class StaffAdapter :
    ListAdapter<JobDetailJson, StaffAdapter.StaffViewHolder>(StaffDiffUtil()) {
    private var detailRows: List<RowDetailJson> = mutableListOf()
    private var job: RateVolumeJson? = null
    private var staff: List<JobDetailJson> = mutableListOf()
    var staffListener: StaffViewHolderListener? = null


    fun replaceData(job: RateVolumeJson, detailRows: List<RowDetailJson>) {
        this.detailRows = detailRows
        this.job = job
        this.staff = job.jobDetail ?: mutableListOf()
        submitList(staff)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemStaffBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.item_staff, parent, false
        )
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        holder.bindView()
    }

    inner class StaffViewHolder(private val binding: ItemStaffBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView() {
            val staff = staff[adapterPosition]
            if (adapterPosition == 0) {
                binding.divider.visibility = View.GONE
            } else {
                binding.divider.visibility = View.VISIBLE
            }
            if (this@StaffAdapter.staff.size > 1) {
                binding.btnAddMaxTree.visibility = View.GONE
            } else {
                binding.btnAddMaxTree.visibility = View.VISIBLE
            }
            binding.tvAva.apply {
                background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_circle_cyan)
                text =
                    "${staff.user?.lastName?.firstOrNull() ?: ""}${staff.user?.firstName?.firstOrNull() ?: ""}"
            }
            binding.tvName.text = "${staff.user?.lastName} ${staff.user?.firstName}"
            binding.tvOrchard.text = staff.orchard?.name
            binding.tvBlock.text = staff.block?.name
            staff.ratetype?.apply {
                bindRateType(this.id)
            }
            binding.btnPieceRate.setOnDebounceClick {
                bindRateType(RateTypeJson.RateType.PIECE_RATE.value)
                staffListener?.onRateTypeClick(
                    RateTypeJson.RateType.PIECE_RATE,
                    staff.id,
                    job?.job?.id
                )
            }
            binding.btnWages.setOnDebounceClick {
                bindRateType(RateTypeJson.RateType.WAGES.value)
                staffListener?.onRateTypeClick(
                    RateTypeJson.RateType.WAGES,
                    staff.id,
                    job?.job?.id
                )
            }
            binding.viewEditRate.edtRateEdit.setText(staff.salary?.rate?.toString())
            binding.viewEditRate.edtRateEdit.setOnEditorActionListener(object :
                TextView.OnEditorActionListener {
                override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == EditorInfo.IME_ACTION_DONE) {
                        staffListener?.onEditRateDone(
                            binding.viewEditRate.edtRateEdit.text.toString(), staff.id,
                            job?.job?.id
                        )
                        return true
                    }
                    return false
                }
            })

            binding.viewEditRate.btnApplyAll.setOnDebounceClick {
                staffListener?.onApplyRateToAll(
                    binding.viewEditRate.edtRateEdit.text.toString(),
                    job?.job?.id
                )
            }
            initListRow()
        }

        private fun initListRow() {
            val listRowParallel = job?.getRowIdParallel()
            val enableColor = ContextCompat.getColorStateList(itemView.context, R.color.blue)
            val disableColor = ContextCompat.getColorStateList(itemView.context, R.color.brown)
            binding.viewRow.removeAllViews()
            binding.llRowTextField.removeAllViews()
            val staff = staff[adapterPosition]
            staff.row?.let {
                for (row in it) {
                    val detailRow = detailRows.getDetailRow(row.id)
                    addRowButton(row, enableColor, disableColor, listRowParallel, detailRow)
                    addEditRowView(row, detailRow)
                }
            }
        }

        private fun addEditRowView(
            row: RowJson,
            rowDetailJson: RowDetailJson?
        ) {
            val treeDone = row.treeDone
            if (treeDone != null && treeDone > 0) {
                val editBinding: ViewEditTreesNumberBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(itemView.context),
                    R.layout.view_edit_trees_number,
                    null,
                    false
                )
                editBinding.tvForRow.text =
                    itemView.context.getString(R.string.tree_for_row, rowDetailJson?.name)
                editBinding.edtNumberOfRow.setText(row.treeDone.toString())
                editBinding.tvRowName.text = itemView.context.getString(
                    R.string.row_name,
                    rowDetailJson?.place,
                    rowDetailJson?.remainTree?.toString()
                )
                binding.llRowTextField.addView(editBinding.root)
            }
        }

        private fun addRowButton(
            row: RowJson,
            enableColor: ColorStateList?,
            disableColor: ColorStateList?,
            listRowParallel: List<String>?,
            detailRow: RowDetailJson?
        ) {
            val rowBinding: ViewRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(itemView.context), R.layout.view_row, null, false
            )
            val treeDone = row.treeDone
            if (treeDone != null && treeDone > 0) {
                rowBinding.btnRow.backgroundTintList = enableColor
            } else {
                rowBinding.btnRow.backgroundTintList = disableColor
            }
            if (listRowParallel?.contains(row.id) == true) {
                rowBinding.viewDot.visibility = View.VISIBLE
            } else {
                rowBinding.viewDot.visibility = View.GONE
            }
            rowBinding.btnRow.text = detailRow?.name

            binding.viewRow.addView(rowBinding.root)
        }

        private fun bindRateType(id: String?) {
            val context = itemView.context
            val enableColor = ContextCompat.getColorStateList(context, R.color.primaryColor)
            val disableColor = ContextCompat.getColorStateList(context, R.color.brown)
            val enableTextColor = ContextCompat.getColor(context, R.color.white)
            val disableTextColor = ContextCompat.getColor(context, R.color.black)
            when (id) {
                RateTypeJson.RateType.PIECE_RATE.value -> {
                    binding.btnPieceRate.backgroundTintList = enableColor
                    binding.btnWages.backgroundTintList = disableColor
                    binding.viewEditRate.root.visibility = View.VISIBLE
                    binding.tvWagesNotice.visibility = View.GONE
                    binding.btnPieceRate.setTextColor(enableTextColor)
                    binding.btnWages.setTextColor(disableTextColor)
                }
                RateTypeJson.RateType.WAGES.value -> {
                    binding.tvWagesNotice.text =
                        itemView.context.getString(R.string.wages_notice, job?.job?.name)
                    binding.btnPieceRate.backgroundTintList = disableColor
                    binding.btnWages.backgroundTintList = enableColor
                    binding.viewEditRate.root.visibility = View.GONE
                    binding.tvWagesNotice.visibility = View.VISIBLE
                    binding.btnPieceRate.setTextColor(disableTextColor)
                    binding.btnWages.setTextColor(enableTextColor)
                }
            }
        }

    }

    class StaffDiffUtil : DiffUtil.ItemCallback<JobDetailJson>() {
        override fun areItemsTheSame(oldItem: JobDetailJson, newItem: JobDetailJson): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: JobDetailJson, newItem: JobDetailJson): Boolean {
            return oldItem.equals(newItem)
        }
    }

    interface StaffViewHolderListener {
        fun onRateTypeClick(rateType: RateTypeJson.RateType, jobDetailId: String?, jobId: String?)
        fun onEditRateDone(rate: String, jobDetailId: String?, jobId: String?)
        fun onApplyRateToAll(rate: String, jobId: String?)
    }
}