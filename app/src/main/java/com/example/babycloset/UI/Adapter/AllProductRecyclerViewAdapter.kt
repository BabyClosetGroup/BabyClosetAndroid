package com.example.babycloset.UI.Adapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.AllPostRVData
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.ProductActivity
import org.jetbrains.anko.startActivity

class AllProductRecyclerViewAdapter(var ctx : Context, var datalist : ArrayList<AllPostRVData>): RecyclerView.Adapter<AllProductRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):Holder {
       val view : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_all_product, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder : Holder, position: Int) {

        val dataList : ArrayList<String> = datalist[position].areaName

        Glide.with(ctx)
            .load(datalist[position].mainImage)
            .centerCrop()
            .into(holder.mainImage)

        holder.postTitle.text = datalist[position].postTitle
        holder.areaName.text = dataList[0] + " 외 " + dataList.size + "구"
        holder.container.setOnClickListener {
            ctx.startActivity<ProductActivity>(
                "postIdx" to datalist[position].postIdx
            )
        }
    }

    inner class Holder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var container = itemView.findViewById(R.id.ll_rv_item_all_product) as LinearLayout
        var postTitle  = itemView.findViewById(R.id.txt_all_product_title) as TextView
        var mainImage = itemView.findViewById(R.id.img_all_product) as ImageView
        var areaName = itemView.findViewById(R.id.txt_all_product_area) as TextView
    }
}