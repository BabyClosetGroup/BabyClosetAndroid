package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.DeadLineProcuctData
import com.example.babycloset.R

class DeadLineProductRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<DeadLineProcuctData>) : RecyclerView.Adapter<DeadLineProductRecyclerViewAdapter.Holder>(){
    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_deadline_product,viewgroup,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx)
            .load(dataList[position].mainImage)
            .into(holder.mainImage)
        holder.postTitle.text = dataList[position].postTitle
        holder.deadLine.text = dataList[position].deadLine
        holder.areaName.text = dataList[position].areaName
    }

    inner class Holder(itemview:View): RecyclerView.ViewHolder(itemview){
        var postTitle = itemview.findViewById(R.id.txt_deadline_product_title) as TextView
        var deadLine = itemview.findViewById(R.id.img_ddline_deadline_product) as Button
        var mainImage = itemview.findViewById(R.id.img_deadline_product) as ImageView
        var areaName = itemview.findViewById(R.id.txt_deadline_product_area) as TextView
    }
}