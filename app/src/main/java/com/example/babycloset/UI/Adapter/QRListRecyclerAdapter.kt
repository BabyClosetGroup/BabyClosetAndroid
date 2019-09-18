package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.QRListData
import com.example.babycloset.R

class QRListRecyclerAdapter (val ctx: Context, val dataList: ArrayList<QRListData>,val itemClick: (QRListData) -> Unit): RecyclerView.Adapter<QRListRecyclerAdapter.Holder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): QRListRecyclerAdapter.Holder {
        val view: View=LayoutInflater.from(ctx).inflate(R.layout.rv_item_qr_list_,p0,false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(dataList[position])
        Glide.with(ctx).load(dataList[position].mainImage).into(holder.qr_list_img)
        holder.qr_list_title.text=dataList[position].postTitle
        holder.qr_list_area.text=dataList[position].areaName[0]  //수정필요
    }

    inner class Holder(itemView: View,itemClick: (QRListData) -> Unit): RecyclerView.ViewHolder(itemView){
        var qr_list_img=itemView.findViewById(R.id.qr_list_img) as ImageView
        var qr_list_title=itemView.findViewById(R.id.qr_list_txt_title) as TextView
        var qr_list_area=itemView.findViewById(R.id.qr_list_txt_area) as TextView

        fun bind(qrListData: QRListData){
            itemView.setOnClickListener { itemClick(qrListData) }
        }
    }
}