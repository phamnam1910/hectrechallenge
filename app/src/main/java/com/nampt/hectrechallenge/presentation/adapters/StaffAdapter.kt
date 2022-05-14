package com.nampt.hectrechallenge.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nampt.hectrechallenge.R

class StaffAdapter : RecyclerView.Adapter<StaffAdapter.VHA>() {

    inner class VHA(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHA {
        return VHA(
            LayoutInflater.from(parent.context).inflate(R.layout.item_staff, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VHA, position: Int) {
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onViewRecycled(holder: VHA) {
        super.onViewRecycled(holder)
        Log.d("inner recycler", holder.layoutPosition.toString())
    }
}