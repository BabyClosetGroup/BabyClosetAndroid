package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.babycloset.Data.EmailData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.EmailMsgActivity
import org.jetbrains.anko.startActivity

class EmailRecyclerAdapter (var ctx: Context, var dataList: ArrayList<EmailData>): RecyclerView.Adapter<EmailRecyclerAdapter.Holder>(){

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_email, p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.text_email_person.text = dataList[position].nickname
        holder.text_email_send_time.text = dataList[position].createdTime
        holder.text_email_msg.text = dataList[position].lastContent
        holder.text_email_msg_num.text = "+"+dataList[position].unreadCount


        holder.ll_email_person.setOnClickListener {
            ctx.startActivity<EmailMsgActivity>(
                    "userIdx" to dataList[position].userIdx,
                    "nickname" to dataList[position].nickname
            )
        }

        if(dataList[position].unreadCount.equals("0")){ //온거 없으면 +(숫자) 없애기
            holder.text_email_msg_num.visibility = INVISIBLE
        }

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var ll_email_person = itemView.findViewById(R.id.ll_email_person) as LinearLayout
        var text_email_person = itemView.findViewById(R.id.text_email_person) as TextView
        var text_email_send_time = itemView.findViewById(R.id.text_email_send_time) as TextView
        var text_email_msg = itemView.findViewById(R.id.text_email_msg) as TextView
        var text_email_msg_num = itemView.findViewById(R.id.text_email_msg_num) as TextView
    }
}