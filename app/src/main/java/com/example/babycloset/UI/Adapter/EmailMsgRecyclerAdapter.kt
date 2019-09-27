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
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R

class EmailMsgRecyclerAdapter (var ctx: Context, var dataList: ArrayList<EmailMsgData>): RecyclerView.Adapter<EmailMsgRecyclerAdapter.Holder>(){

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
/*
    override fun getItemViewType(position: Int): Int { //뷰 종류 구분해서 리턴해주기
        //Log.e("message", "${dataList[position].noteType}    ${dataList[position].noteContent}")
        //Log.e("message", "${position}")
        //return dataList[position].noteType //보낸거면 0, 받은거면 1
    }
*/
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): Holder { //뷰 종류따라 inflate 해주기
        //Log.e("getitem", "${getItemViewType(p1)}")
        //Log.e("position", "${getItemViewType(position)}")
        //Log.e("sended", "${dataList[position].noteType}")
        //if(getItemViewType(position)==0){ //보낸쪽지(나)
        if(dataList[position].noteType == 0){
            //Log.e("message", "보낸 쪽지   ${dataList[p1].noteContent}")
            var view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_email_msg_send, viewGroup,false)
            return Holder(view)
        }
        else { //받은쪽지(상대방)
            //Log.e("message", "받은 쪽지   ${dataList[p1].noteContent}")
            var view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_email_msg_receive, viewGroup, false)
            return Holder(view)
        }

    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.rv_txt_email_msg_time.text = dataList[position].createdTime
        holder.rv_txt_email_msg_contents.text = dataList[position].noteContent
//        holder.rv_txt_email_msg_what.text = dataList[position].

    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var rv_txt_email_msg_time = itemView.findViewById(R.id.rv_txt_email_msg_time) as TextView
        var rv_txt_email_msg_contents = itemView.findViewById(R.id.rv_txt_email_msg_contents) as TextView
        //var rv_txt_email_msg_what = itemView.findViewById(R.id.rv_txt_email_msg_what) as TextView
    }

}