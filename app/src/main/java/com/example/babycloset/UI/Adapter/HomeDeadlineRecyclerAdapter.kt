package com.example.babycloset.UI.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.babycloset.Data.HomeDeadlineData
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.ProductActivity
import kotlinx.android.synthetic.main.rv_item_home_deadline.*
import kotlinx.android.synthetic.main.rv_item_home_deadline.view.*
import kotlinx.android.synthetic.main.rv_item_home_recent.*

class HomeDeadlineRecyclerAdapter (val ctx: Context, val dataList: ArrayList<HomeDeadlineData>?): RecyclerView.Adapter<HomeDeadlineRecyclerAdapter.Holder>(){
    var postIdx = -1
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeDeadlineRecyclerAdapter.Holder {
        val view: View=LayoutInflater.from(ctx).inflate(R.layout.rv_item_home_deadline,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList!!.size

    override fun onBindViewHolder(holder: Holder, p1: Int) {
        //이미지에 rounding 주기
        val drawable: GradientDrawable = ctx.getDrawable(R.drawable.img_background_rounding) as GradientDrawable
        holder.img_deadline.background = drawable
        holder.img_deadline.clipToOutline = true

        Glide.with(ctx).load(dataList!![p1].mainImage).into(holder.img_deadline)
        holder.txt_deadline_day.text=dataList!![p1].deadline
        holder.txt_title.text=dataList!![p1].postTitle
        holder.txt_area.text=dataList!![p1].areaName[0] //수정 필요

        if(dataList!![p1].areaName!!.size == 1){
            holder.txt_area_etc.text=""
        }else if(dataList!![p1].areaName!!.size > 1){
            var size= dataList!![p1].areaName!!.size - 1
            holder.txt_area_etc.text="외 " + size.toString() + "구"
        }
        holder.relative_layout.setOnClickListener {
            postIdx=dataList!!.get(p1).postIdx
            var intent= Intent(ctx,ProductActivity::class.java)
            intent.putExtra("postIdx",postIdx)
            ctx.startActivity(intent)
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img_deadline=itemView.findViewById(R.id.img_home_deadline) as ImageView
        var txt_deadline_day=itemView.findViewById(R.id.txt_home_deadline_day) as TextView
        var txt_title=itemView.findViewById(R.id.txt_home_deadline_title) as TextView
        var txt_area=itemView.findViewById(R.id.txt_home_deadline_area) as TextView
        var txt_area_etc=itemView.findViewById(R.id.txt_home_deadline_area_etc) as TextView
        var relative_layout=itemView.findViewById(R.id.rl_home_deadline_product) as RelativeLayout
    }
}