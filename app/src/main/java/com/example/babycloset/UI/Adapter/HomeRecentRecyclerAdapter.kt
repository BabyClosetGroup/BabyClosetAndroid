package com.example.babycloset.UI.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.babycloset.Data.HomeRecentData
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.ProductActivity

class HomeRecentRecyclerAdapter (val ctx: Context, val dataList: ArrayList<HomeRecentData>?): RecyclerView.Adapter<HomeRecentRecyclerAdapter.Holder>(){
    var postIdx=-1
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeRecentRecyclerAdapter.Holder {
        val view: View=LayoutInflater.from(ctx).inflate(R.layout.rv_item_home_recent,p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList!!.size

    override fun onBindViewHolder(holder: Holder, p1: Int) {
        //이미지에 rounding 주기
        val drawable: GradientDrawable = ctx.getDrawable(R.drawable.img_background_rounding) as GradientDrawable
        holder.img_recent.background = drawable
        holder.img_recent.clipToOutline = true

        Glide.with(ctx).load(dataList!![p1].mainImage).into(holder.img_recent)
        holder.txt_title.text=dataList!![p1].postTitle
        //areaName이 null이 들어올 경우
        if(dataList!![p1].areaName==null){
            holder.txt_area.text="강남가라"
            holder.txt_area_etc.text="1212"
        }else{
            holder.txt_area.text=dataList!![p1].areaName!![0] //수정필요
            holder.txt_area_etc.text=dataList!![p1].areaName!!.size.toString()
        }

        holder.relative_layout.setOnClickListener {
            postIdx=dataList!!.get(p1).postIdx
            var intent= Intent(ctx,ProductActivity::class.java)
            intent.putExtra("postIdx",postIdx)
            ctx.startActivity(intent)
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img_recent=itemView.findViewById(R.id.img_home_recent) as ImageView
        var txt_title=itemView.findViewById(R.id.txt_home_recent_title) as TextView
        var txt_area=itemView.findViewById(R.id.txt_home_recent_area) as TextView
        var txt_area_etc=itemView.findViewById(R.id.txt_home_recent_area_etc) as TextView
        var relative_layout=itemView.findViewById(R.id.rl_home_recent_product) as RelativeLayout
    }
}