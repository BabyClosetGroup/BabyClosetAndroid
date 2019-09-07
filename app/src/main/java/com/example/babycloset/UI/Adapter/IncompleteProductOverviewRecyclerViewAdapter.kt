package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.IncompleteProductOverviewData
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.ListPeopleActivity
import com.example.babycloset.UI.Activity.RatingActivity
import org.jetbrains.anko.image
import org.jetbrains.anko.startActivity

class IncompleteProductOverviewRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<IncompleteProductOverviewData>): RecyclerView.Adapter<IncompleteProductOverviewRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx)
            .inflate(com.example.babycloset.R.layout.rv_incomplete_product_overview, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx).load(dataList[position].mainImage).into(holder.thumbnail)
        //holder.title.text = dataList[position].productTitle
        holder.location.text = dataList[position].areaName
        holder.num.text = dataList[position].registerNumber

        holder.btn.setOnClickListener {
            ctx.startActivity<ListPeopleActivity>()
        }
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var container = itemView.findViewById(R.id.btn_rv_item_incomplete_overview_share) as RelativeLayout
        //var title = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_incomplete_overview_product) as TextView
        var location = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_incomplete_overview_location) as TextView
        var num = itemView.findViewById(R.id.txt_rv_item_incomplete_overview_number) as TextView
        var btn = itemView.findViewById(R.id.btn_rv_item_incomplete_overview_share) as RelativeLayout
        var thumbnail = itemView.findViewById(R.id.img_rv_item_incomplete_overview_thumbnail) as ImageView
    }
}