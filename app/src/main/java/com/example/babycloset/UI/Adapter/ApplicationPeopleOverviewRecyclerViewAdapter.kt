package com.example.babycloset.UI.Adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.babycloset.Data.ApplicationPeopleOverviewData
import com.example.babycloset.Data.CompleteProductOverviewData
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.RatingActivity
import com.example.babycloset.UI.Activity.ShareProductActivity
import com.example.babycloset.UI.Fragment.ShareCompleteFragment
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ApplicationPeopleOverviewRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<ApplicationPeopleOverviewData>): RecyclerView.Adapter<ApplicationPeopleOverviewRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx)
            .inflate(com.example.babycloset.R.layout.rv_application_people_overview, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
       // Glide.with(ctx).load(RVDataList[position].productImg).into(holder.thumbnail)
        holder.name.text = dataList[position].applicantNickname
        holder.rate.text = dataList[position].rating.toString()+"점"
        var score = dataList[position].rating.toFloat()
        holder.rb.rating= score
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_application_account_name) as TextView
        var rate = itemView.findViewById(R.id.txt_rv_item_application_score) as TextView
        var rb = itemView.findViewById(R.id.rb_rv_item_application_rate) as RatingBar
        //var thumbnail = itemView.findViewById(R.id.img_rv_item_application_overview_profile) as ImageView
    }
}