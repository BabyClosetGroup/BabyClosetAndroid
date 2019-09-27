package com.example.babycloset.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.babycloset.Data.EmailMsgData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import org.jetbrains.anko.textColorResource

class EmailMsgRecyclerAdapter (var ctx: Context, var dataList: ArrayList<EmailMsgData>): RecyclerView.Adapter<EmailMsgRecyclerAdapter.Holder>(){

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun getItemViewType(position: Int): Int { //뷰 종류 구분해서 리턴해주기

        return dataList[position].noteType //받은 쪽지가 0, 보낸 쪽지가 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): Holder { //뷰 종류따라 inflate 해주기
        var view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_email_msg_receive, viewGroup, false)
        return Holder(view)

    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        if(getItemViewType(position)==1) { //보낸 쪽지 (회색)
            holder.rv_border.setBackgroundResource(R.drawable.email_msg_send_border)
            holder.rv_txt_email_msg_what.text = "보낸 쪽지"
            holder.rv_txt_email_msg_what.setTextColor(R.color.grey) //빨간줄 뜨는데 이상하게 걍 둬도 잘됨
        }
        else { //받은 쪽지 (노란색)
            holder.rv_border.setBackgroundResource(R.drawable.email_msg_receive_border)
            holder.rv_txt_email_msg_what.text = "받은 쪽지"
        }

        holder.rv_txt_email_msg_time.text = dataList[position].createdTime
        holder.rv_txt_email_msg_contents.text = dataList[position].noteContent

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var rv_txt_email_msg_time = itemView.findViewById(R.id.rv_txt_email_msg_time) as TextView
        var rv_txt_email_msg_contents = itemView.findViewById(R.id.rv_txt_email_msg_contents) as TextView
        var rv_border = itemView.findViewById(R.id.borderrr) as LinearLayout
        var rv_txt_email_msg_what = itemView.findViewById(R.id.rv_txt_email_msg_what) as TextView
    }

}