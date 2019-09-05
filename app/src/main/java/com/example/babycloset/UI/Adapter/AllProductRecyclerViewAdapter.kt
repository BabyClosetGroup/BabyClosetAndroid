package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.AllProductData
import com.example.babycloset.R

class AllProductRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<AllProductData>): RecyclerView.Adapter<AllProductRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):Holder {
       val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_all_product, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder : Holder, position: Int) {
        Glide.with(ctx)
            .load(dataList[position].mainImage)
            .into(holder.mainImage)
        holder.postTitle.text = dataList[position].postTitle
        holder.areaName.text = dataList[position].areaName


        holder.container.setOnClickListener {
            Log.e("title", dataList[position].postTitle)
        }


    }

    inner class Holder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var container = itemView.findViewById(R.id.ll_rv_item_all_product) as LinearLayout
        var postTitle  = itemView.findViewById(R.id.txt_all_product_title) as TextView
        var mainImage = itemView.findViewById(R.id.img_all_product) as ImageView
        var areaName = itemView.findViewById(R.id.txt_all_product_area) as TextView
    }
}