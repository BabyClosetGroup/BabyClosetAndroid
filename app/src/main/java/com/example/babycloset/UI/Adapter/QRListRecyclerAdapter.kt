package com.example.babycloset.UI.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.babycloset.Data.QRListData
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.QRCreateActivity

class QRListRecyclerAdapter (val ctx: Context, val dataList: ArrayList<QRListData>): RecyclerView.Adapter<QRListRecyclerAdapter.Holder>(){
    var postindex: Int=-1
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): QRListRecyclerAdapter.Holder {
        val view: View=LayoutInflater.from(ctx).inflate(R.layout.rv_item_qr_list_,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(ctx).load(dataList[position].mainImage).into(holder.qr_list_img)
        holder.qr_list_title.text=dataList[position].postTitle
        holder.qr_list_area.text=dataList[position].areaName[0]  //수정필요
        holder.qr_list_area_etc.text=dataList[position].areaName.size.toString()
        holder.qr_list_button.setOnClickListener {
            postindex = dataList.get(position).postIdx
            var intent= Intent(ctx, QRCreateActivity::class.java)
            intent.putExtra("postindex",postindex)
            ctx.startActivity(intent)
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var qr_list_img=itemView.findViewById(R.id.qr_list_img) as ImageView
        var qr_list_title=itemView.findViewById(R.id.qr_list_txt_title) as TextView
        var qr_list_area=itemView.findViewById(R.id.qr_list_txt_area) as TextView
        var qr_list_area_etc=itemView.findViewById(R.id.txt_qr_list_area_etc) as TextView
        var qr_list_button=itemView.findViewById(R.id.qr_list_btn_create) as Button
    }
}