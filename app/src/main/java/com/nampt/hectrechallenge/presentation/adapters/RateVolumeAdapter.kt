package com.nampt.hectrechallenge.presentation.adapters

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.nampt.hectrechallenge.R
import com.nampt.hectrechallenge.databinding.*
import com.nampt.hectrechallenge.domain.model.RateTypeJson
import com.nampt.hectrechallenge.domain.model.RowDetailJson
import com.nampt.hectrechallenge.domain.model.RowJson
import com.nampt.hectrechallenge.domain.util.getDetailRow
import com.nampt.hectrechallenge.domain.util.getRowIdParallel
import com.nampt.hectrechallenge.util.setOnDebounceClick

class RateVolumeAdapter :
    RecyclerView.Adapter<RateVolumeAdapter.RateVolumeViewHolder<RateVolumeItem>>() {

    interface JobViewHolderListener {
        fun onAddMaxTreeClick(id: String?, position: Int)
        fun onRowClick(rowId: String?, detailJobId: String?, jobId: String?)
        fun onApplyRate(rate: String?, jobId: String?)
    }

    var jobListener: JobViewHolderListener? = null

    private var listData = mutableListOf<RateVolumeItem>()
    private var detailRows: List<RowDetailJson> = mutableListOf()

    //    private var job: RateVolumeJson? = null
//    private var staff: List<JobDetailJson> = mutableListOf()

    fun addDetailRows(detailRows: List<RowDetailJson>?) {
        if (detailRows != null) {
            this.detailRows = detailRows
        }
    }

    fun replaceData(listData: List<RateVolumeItem>) {
        this.listData = listData.toMutableList()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RateVolumeViewHolder<RateVolumeItem> {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            RateVolumeItemViewType.Header.value -> {
                return HeaderViewHolder(
                    ItemHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                ) as RateVolumeViewHolder<RateVolumeItem>
            }
            RateVolumeItemViewType.Body.value -> {
                return BodyViewHolder(
                    ItemStaffBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                ) as RateVolumeViewHolder<RateVolumeItem>
            }
        }
        return FooterViewHolder(
            ItemFooterBinding.inflate(
                inflater,
                parent,
                false
            )
        ) as RateVolumeViewHolder<RateVolumeItem>
    }

    override fun onBindViewHolder(holder: RateVolumeViewHolder<RateVolumeItem>, position: Int) {
        holder.bindView(listData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].viewType
    }

    abstract inner class RateVolumeViewHolder<T>(viewBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        open fun bindView(item: T) {

        }
    }

    inner class HeaderViewHolder(val binding: ItemHeaderBinding) :
        RateVolumeViewHolder<RateVolumeItem.HeaderItem>(binding) {
        override fun bindView(item: RateVolumeItem.HeaderItem) {
            val job = item.JobJson
            binding.tvJob.text = job?.name
            binding.btnAddMaxTree.apply {
                setOnDebounceClick {
                    jobListener?.onAddMaxTreeClick(job?.id, adapterPosition)
                }
            }
        }
    }

    inner class BodyViewHolder(val binding: ItemStaffBinding) :
        RateVolumeViewHolder<RateVolumeItem.BodyItem>(binding) {

        private val enableColor = ContextCompat.getColorStateList(itemView.context, R.color.blue)
        private val disableColor = ContextCompat.getColorStateList(itemView.context, R.color.brown)

        override fun bindView(item: RateVolumeItem.BodyItem) {
            val staff = item.staff ?: return
            if (adapterPosition == 0) {
                binding.divider.visibility = View.GONE
            } else {
                binding.divider.visibility = View.VISIBLE
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
                bindRateType(this.id, item)
            }
            binding.btnPieceRate.setOnDebounceClick {
                bindRateType(RateTypeJson.RateType.PIECE_RATE.value, item)
//                staffListener?.onRateTypeClick(
//                    RateTypeJson.RateType.PIECE_RATE,
//                    staff.id,
//                    job?.job?.id
//                )
            }
            binding.btnWages.setOnDebounceClick {
                bindRateType(RateTypeJson.RateType.WAGES.value, item)
//                staffListener?.onRateTypeClick(
//                    RateTypeJson.RateType.WAGES,
//                    staff.id,
//                    job?.job?.id
//                )
            }
            binding.viewEditRate.edtRateEdit.setText(staff.salary?.rate?.toString())
            binding.viewEditRate.edtRateEdit.setOnEditorActionListener(object :
                TextView.OnEditorActionListener {
                override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == EditorInfo.IME_ACTION_DONE) {
//                        staffListener?.onEditRateDone(
//                            binding.viewEditRate.edtRateEdit.text.toString(), staff.id,
//                            job?.job?.id
//                        )
                        return true
                    }
                    return false
                }
            })

            binding.viewEditRate.btnApplyAll.setOnDebounceClick {
                jobListener?.onApplyRate(
                    binding.viewEditRate.edtRateEdit.text.toString(),
                    item.jobDetail.job?.id
                )
            }
            initListRow(item)
        }

        private fun initListRow(item: RateVolumeItem.BodyItem) {
            val listRowParallel = item.jobDetail.getRowIdParallel()

            binding.viewRow.removeAllViews()
            binding.llRowTextField.removeAllViews()
            val staff = item.staff
            staff?.row?.let {
                for (row in it) {
                    val detailRow = detailRows.getDetailRow(row.id)
                    addRowButton(row, listRowParallel, detailRow, item)
                    addEditRowView(row, detailRow)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun addEditRowView(
            row: RowJson,
            rowDetailJson: RowDetailJson?
        ) {
            val treeDone = row.treeDone
            if (treeDone != null) {
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
                editBinding.tvTotalTree.text = "/${rowDetailJson?.totalTree}"
                binding.llRowTextField.addView(editBinding.root)
            }
        }

        private fun addRowButton(
            row: RowJson,
            listRowParallel: List<String>?,
            detailRow: RowDetailJson?,
            item: RateVolumeItem.BodyItem
        ) {
            val rowBinding: ViewRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(itemView.context), R.layout.view_row, null, false
            )
            val treeDone = row.treeDone
            if (treeDone != null) {
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
            rowBinding.btnRow.setOnDebounceClick {
                jobListener?.onRowClick(
                    row.id,
                    item.staff?.id,
                    item.jobDetail.job?.id
                )
            }
            binding.viewRow.addView(rowBinding.root)
        }

        private fun bindRateType(id: String?, item: RateVolumeItem.BodyItem) {
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
                        itemView.context.getString(
                            R.string.wages_notice,
                            item?.jobDetail?.job?.name
                        )
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

    inner class FooterViewHolder(viewBinding: ItemFooterBinding) :
        RateVolumeViewHolder<RateVolumeItem.FooterItem>(viewBinding) {
        override fun bindView(item: RateVolumeItem.FooterItem) {

        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}