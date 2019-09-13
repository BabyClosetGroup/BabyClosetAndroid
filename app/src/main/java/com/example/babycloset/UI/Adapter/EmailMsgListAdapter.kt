package com.example.babycloset.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.babycloset.Data.EmailMsgData
import com.example.babycloset.R

class EmailMsgListAdapter(val context: Context, val emailMsgList : ArrayList<EmailMsgData>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_email_msg_send, null)

        val sendTime = view.findViewById<TextView>(R.id.listview_email_send_msg_time)
        val sendMsg = view.findViewById<TextView>(R.id.listview_email_send_msg_contents)

        val emailMsg = emailMsgList[position]
        sendTime.text = emailMsg.msgSendTime
        sendMsg.text = emailMsg.msgContents

        return view
    }

    override fun getItem(position: Int): Any {
        return emailMsgList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0 //id반환하는걸로 바꾸기
    }

    override fun getCount(): Int {
        return emailMsgList.size
    }
}