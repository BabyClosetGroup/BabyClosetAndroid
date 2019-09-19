package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.R

class CategoryRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<CategoryData>) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): Holder {
        val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_category, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount():Int =  dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.category.text = dataList[position].category
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var category = itemView.findViewById<Button>(R.id.txt_rv_item_category)
    }

}