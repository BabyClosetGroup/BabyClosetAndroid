package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.babycloset.Data.EmailData
import com.example.babycloset.R
import com.example.babycloset.UI.Activity.EmailMsgActivity
import org.jetbrains.anko.startActivity

class EmailRecyclerAdapter (val ctx: Context, val dataList: ArrayList<EmailData>): RecyclerView.Adapter<EmailRecyclerAdapter.Holder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_email, p0,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.text_email_person.text = dataList[position].personName
        holder.text_email_send_time.text = dataList[position].sendTime
        holder.text_email_msg.text = dataList[position].emailMsg
        holder.text_email_msg_num.text = dataList[position].msgNum

        holder.ll_email_person.setOnClickListener {
            Log.e("email", dataList[position].personName)
            ctx.startActivity<EmailMsgActivity>()
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