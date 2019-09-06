package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.HomeRecentData
import com.example.babycloset.R

class HomeRecentRecyclerAdapter (val ctx: Context, val dataList: ArrayList<HomeRecentData>): RecyclerView.Adapter<HomeRecentRecyclerAdapter.Holder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeRecentRecyclerAdapter.Holder {
        val view: View=LayoutInflater.from(ctx).inflate(R.layout.rv_item_home_recent,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, p1: Int) {
        Glide.with(ctx).load(dataList[p1].mainImage).into(holder.img_recent)
        holder.txt_title.text=dataList[p1].postTitle
        holder.txt_area.text=dataList[p1].areaName
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img_recent=itemView.findViewById(R.id.img_home_recent) as ImageView
        var txt_title=itemView.findViewById(R.id.txt_home_recent_title) as TextView
        var txt_area=itemView.findViewById(R.id.txt_home_recent_area) as TextView
    }
}