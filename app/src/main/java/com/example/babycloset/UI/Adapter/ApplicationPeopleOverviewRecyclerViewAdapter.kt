package com.example.babycloset.UI.Adapter

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

    var nn:String=""
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx)
            .inflate(com.example.babycloset.R.layout.rv_application_people_overview, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
       // Glide.with(ctx).load(dataList[position].productImg).into(holder.thumbnail)
        holder.name.text = dataList[position].applicantNickname
        holder.rate.text = dataList[position].rating.toString()+"점"
        var score = dataList[position].rating.toFloat()
        holder.rb.rating= score

        holder.container.setOnClickListener {
            nn=dataList[position].applicantNickname
            showMailDialog()
        }
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container = itemView.findViewById(R.id.ll_rv_item_application_overview_container) as LinearLayout
        var name = itemView.findViewById(com.example.babycloset.R.id.txt_rv_item_application_account_name) as TextView
        var rate = itemView.findViewById(R.id.txt_rv_item_application_score) as TextView
        var rb = itemView.findViewById(R.id.rb_rv_item_application_rate) as RatingBar
        //var thumbnail = itemView.findViewById(R.id.img_rv_item_application_overview_profile) as ImageView
    }
    private fun showMailDialog() {
        val MailDialog = AlertDialog.Builder(ctx)
        MailDialog.setTitle("")
        MailDialog.setMessage(nn+"님께 쪽지를 보낼까요?")

        /*var dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        //쪽지창으로

                    DialogInterface.BUTTON_NEGATIVE ->
                        finish()
                        //toast("취소되었습니다")
                }
            }
        }*/
        MailDialog.setPositiveButton("예",null)
        MailDialog.show()
    }
}