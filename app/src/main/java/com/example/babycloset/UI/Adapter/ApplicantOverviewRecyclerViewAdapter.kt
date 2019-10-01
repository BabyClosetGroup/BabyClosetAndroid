package com.example.babycloset.UI.Adapter

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.babycloset.Data.ApplicantOverviewData
import org.jetbrains.anko.startActivity
import com.example.babycloset.UI.Activity.EmailMsgActivity


class ApplicantOverviewRecyclerViewAdapter(val ctx: Context, var dataList: ArrayList<ApplicantOverviewData>): RecyclerView.Adapter<ApplicantOverviewRecyclerViewAdapter.Holder>() {

    var curIdx:Int=0
    var curName:String=""
    var nickname:String = ""
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx)
            .inflate(com.example.babycloset.R.layout.rv_applicant_overview, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.thumbnail.setBackground(ShapeDrawable(OvalShape()))
        if (Build.VERSION.SDK_INT >= 21) {
            holder.thumbnail.setClipToOutline(true)
        }
        if(dataList[position].profileImage==null){
            holder.thumbnail.setImageResource(com.example.babycloset.R.drawable.user)
        } else
            Glide.with(ctx).load(dataList[position].profileImage).into(holder.thumbnail)
        holder.name.text = dataList[position].applicantNickname
        nickname = dataList[position].applicantNickname

        holder.rate.text = dataList[position].rating.toString()+"점"
        var score = dataList[position].rating
        holder.rb.rating= score

        holder.container.setOnClickListener {
            curIdx=dataList[position].applicantIdx
            curName=dataList[position].applicantNickname
            showMailDialog()
        }
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container = itemView.findViewById(com.example.babycloset.R.id.ll_rv_item_applicant_overview_container) as LinearLayout
        var name = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_applicant_account_name) as TextView
        var rate = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_applicant_score) as TextView
        var rb = itemView.findViewById(com.example.babycloset.R.id.rb_rv_item_applicant_rate) as RatingBar
        var thumbnail = itemView.findViewById(com.example.babycloset.R.id.img_rv_item_applicant_overview_profile) as ImageView
    }
    private fun showMailDialog() {
        val builder = AlertDialog.Builder(ctx)
        builder.setMessage("쪽지를 보내시겠습니까?")
        builder.setPositiveButton(
            "네"
        ) { dialog, which -> ctx.startActivity<EmailMsgActivity>(
                "userIdx" to curIdx,
                "nickname" to curName
        ) }
        builder.setNegativeButton(
            "아니오"
        ) { dialog, which -> null }
        builder.show()
    }
}