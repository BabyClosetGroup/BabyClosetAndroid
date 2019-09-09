package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.babycloset.Data.EmailPersonData
import com.example.babycloset.R

class EmailPersonRecyclerAdapter (val ctx: Context, val dataList: ArrayList<EmailPersonData>): RecyclerView.Adapter<EmailPersonRecyclerAdapter.Holder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EmailPersonRecyclerAdapter.Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_email_people, p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.text_email_person.text = dataList[position].personName
        holder.text_email_send_time.text = dataList[position].sendTime
        holder.text_email_msg.text = dataList[position].emailMsg
        holder.text_email_msg_num.text = dataList[position].msgNum
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var text_email_person = itemView.findViewById(R.id.text_email_person) as TextView
        var text_email_send_time = itemView.findViewById(R.id.text_email_send_time) as TextView
        var text_email_msg = itemView.findViewById(R.id.text_email_msg) as TextView
        var text_email_msg_num = itemView.findViewById(R.id.text_email_msg_num) as TextView
    }
}