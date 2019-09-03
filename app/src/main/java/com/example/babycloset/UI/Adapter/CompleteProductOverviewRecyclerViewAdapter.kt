package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.CompleteProductOverviewData
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.RatingActivity
import org.jetbrains.anko.startActivity

class CompleteProductOverviewRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<CompleteProductOverviewData>): RecyclerView.Adapter<CompleteProductOverviewRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx)
            .inflate(com.example.babycloset.R.layout.rv_complete_product_overview, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
       // Glide.with(ctx).load(dataList[position].productImg).into(holder.thumbnail)
        holder.title.text = dataList[position].productTitle
        holder.location.text = dataList[position].productLocation
        holder.date.text = dataList[position].productDate
        holder.owner.text = dataList[position].productOwner
        if(dataList[position].productRate.equals("미부여"))
            holder.rate.text = dataList[position].productRate
        else
            holder.rate.text = dataList[position].productRate+"점"

        if(!holder.rate.text.equals("미부여")){
            holder.btn.visibility=View.GONE
        }
        holder.info.setOnClickListener {
            // 팝업창
        }
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var container = itemView.findViewById(R.id.ll_rv_item_complete_overview_container) as RelativeLayout
        var title = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_complete_overview_product) as TextView
        var location = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_complete_overview_location) as TextView
        var date = itemView.findViewById(R.id.txt_rv_item_complete_overview_date) as TextView
        var owner = itemView.findViewById(R.id.txt_rv_item_complete_overview_name) as TextView
        var rate = itemView.findViewById(R.id.txt_rv_item_complete_overview_rate) as TextView
        var btn = itemView.findViewById(R.id.btn_rv_item_complete_overview_rate) as RelativeLayout
        var info = itemView.findViewById(R.id.btn_rv_item_complete_overview_info) as ImageView
        //var thumbnail = itemView.findViewById(R.id.img_rv_item_complete_overview_thumbnail) as ImageView
    }
}