package com.nampt.hectrechallenge.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nampt.hectrechallenge.R

class JobAdapter : RecyclerView.Adapter<JobAdapter.VHA>() {

    inner class VHA(val view: View):RecyclerView.ViewHolder(view) {
             fun bindView() {
                 view.findViewById<RecyclerView>(R.id.rcv_staff).adapter = StaffAdapter()
             }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHA {
        return VHA(LayoutInflater.from(parent.context).inflate(R.layout.item_job,parent,false))
    }

    override fun onBindViewHolder(holder: VHA, position: Int) {
        holder.bindView()
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onViewRecycled(holder: VHA) {
        super.onViewRecycled(holder)
        Log.d("outer recycler", holder.layoutPosition.toString())
    }
}