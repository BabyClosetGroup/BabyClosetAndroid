package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.HomeDeadlineData
import com.example.babycloset.R
import kotlinx.android.synthetic.main.rv_item_home_deadline.view.*

class HomeDeadlineRecyclerAdapter (val ctx: Context, val dataList: ArrayList<HomeDeadlineData>): RecyclerView.Adapter<HomeDeadlineRecyclerAdapter.Holder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeDeadlineRecyclerAdapter.Holder {
        val view: View=LayoutInflater.from(ctx).inflate(R.layout.rv_item_home_deadline,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, p1: Int) {
        Glide.with(ctx).load(dataList[p1].mainImage).into(holder.img_deadline)
        holder.txt_deadline_day.text=dataList[p1].deadline
        holder.txt_title.text=dataList[p1].postTitle
        holder.txt_area.text=dataList[p1].areaName[0]
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img_deadline=itemView.findViewById(R.id.img_home_deadline) as ImageView
        var txt_deadline_day=itemView.findViewById(R.id.txt_home_deadline_day) as TextView
        var txt_title=itemView.findViewById(R.id.txt_home_deadline_title) as TextView
        var txt_area=itemView.findViewById(R.id.txt_home_deadline_area) as TextView
    }
}