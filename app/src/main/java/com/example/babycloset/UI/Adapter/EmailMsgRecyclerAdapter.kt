package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.example.babycloset.Data.EmailMsgData
import com.example.babycloset.R

class EmailMsgRecyclerAdapter (val ctx: Context, val dataList: ArrayList<EmailMsgData>): RecyclerView.Adapter<EmailMsgRecyclerAdapter.Holder>(){

    override fun getItemViewType(position: Int): Int { //뷰 종류 구분해서 리턴해주기
        return dataList[position].personIdx
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder { //뷰 종류따라 inflate 해주기
        if(getItemViewType(p1) == 0){ //나
            var view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_email_msg_send, p0,false)
            return Holder(view)
        }
        else{ //상대방
            var view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_email_msg_receive, p0,false)
            return Holder(view)
        }

    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.rv_txt_email_msg_time.text = dataList[position].msgSendTime
        holder.rv_txt_email_msg_contents.text = dataList[position].msgContents

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var rv_txt_email_msg_time = itemView.findViewById(R.id.rv_txt_email_msg_time) as TextView
        var rv_txt_email_msg_contents = itemView.findViewById(R.id.rv_txt_email_msg_contents) as TextView
    }

}