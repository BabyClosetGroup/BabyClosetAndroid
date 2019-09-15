package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View.*
import android.widget.LinearLayout
import com.example.babycloset.Data.EmailMsgData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.EmailMsgRecyclerAdapter
import kotlinx.android.synthetic.main.activity_email_msg.*

class EmailMsgActivity : AppCompatActivity() {
    lateinit var emailMsgRecyclerAdapter: EmailMsgRecyclerAdapter
    var emailMsgList = arrayListOf<EmailMsgData>(
        EmailMsgData(0, "19/01/12 15:51", "축하합니다. 여아 투피스 나눔자로 선정 되셨어요!")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_msg)

        configureList()

        btn_send_msg.setOnClickListener {
            //쪽지 리스트에 추가
        }

    }

    private fun configureList(){
        var dataList: ArrayList<EmailMsgData> = ArrayList()

//        if(dataList.size == 0) { //디폴트 글자 나오는 경우
//            rv_email_msg.visibility = GONE
//            ll_text_email_default.visibility = VISIBLE
//        }

        ll_text_email_default.visibility = GONE

        dataList.add(EmailMsgData(0,"19/01/12 15:51","축하합니다. 여아 투피스 나눔자로 선정 되셨어요!"))
        dataList.add(EmailMsgData(1,"19/01/12 15:55","우와! 감사합니다:)"))

        //rv_email_msg.scrollToPosition(emailMsgRecyclerAdapter.itemCount-1) //맨 마지막 쪽지로 스크롤

        emailMsgRecyclerAdapter= EmailMsgRecyclerAdapter(this,dataList)
        rv_email_msg.adapter = emailMsgRecyclerAdapter
        rv_email_msg.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false)
    }
}
