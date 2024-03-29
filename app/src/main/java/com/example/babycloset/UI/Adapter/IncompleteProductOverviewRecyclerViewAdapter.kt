package com.example.babycloset.UI.Adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
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

class IncompleteProductOverviewRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<IncompleteProductOverviewData>?): RecyclerView.Adapter<IncompleteProductOverviewRecyclerViewAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx)
            .inflate(com.example.babycloset.R.layout.rv_incomplete_product_overview, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList!!.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val drawable: GradientDrawable = ctx.getDrawable(R.drawable.img_background_rounding) as GradientDrawable
        holder.thumbnail.background = drawable
        holder.thumbnail.clipToOutline = true

        Glide.with(ctx).load(dataList!![position].mainImage).into(holder.thumbnail)
        holder.title.text = dataList!![position].postTitle
        val locList:ArrayList<String> = dataList!![position].areaName
        if(locList.size-1!=0){
            holder.location.text = locList[0]
            holder.extra_loc.text ="외 "+(locList.size-1)+"구"
        } else{
            holder.location.text = locList[0]
            holder.extra_loc.text =""
        }

        holder.num.text = dataList!![position].registerNumber

        //나눔하기 누르면 신청자목록으로
        holder.btn.setOnClickListener {
            ctx.startActivity<ListPeopleActivity>(
                "postIdx" to dataList!![position].postIdx,
                "mainImage" to dataList!![position].mainImage,
                "postTitle" to dataList!![position].postTitle,
                "registerNumber" to dataList!![position].registerNumber
            )
        }
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_incomplete_overview_product) as TextView
        var location = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_incomplete_overview_location) as TextView
        var num = itemView.findViewById(R.id.txt_rv_item_incomplete_overview_number) as TextView
        var btn = itemView.findViewById(R.id.btn_rv_item_incomplete_overview_share) as RelativeLayout
        var thumbnail = itemView.findViewById(R.id.img_rv_item_incomplete_overview_thumbnail) as ImageView
        var extra_loc = itemView.findViewById(R.id.txt_rv_item_incomplete_overview_location_extra) as TextView
    }
}