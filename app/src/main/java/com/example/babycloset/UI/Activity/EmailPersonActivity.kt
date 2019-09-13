package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.Data.EmailMsgData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.EmailMsgListAdapter
import kotlinx.android.synthetic.main.activity_email_person.*
import kotlinx.android.synthetic.main.listview_email_msg_send.*

class EmailPersonActivity : AppCompatActivity() {

    var emailMsgList = arrayListOf<EmailMsgData>(
        EmailMsgData(0, "19/01/12 15:51", "축하합니다. 여아 투피스 나눔자로 선정 되셨어요!")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_person)

        val emailSendAdapter = EmailMsgListAdapter(this, emailMsgList)
        listview_email_msg_send.adapter = emailSendAdapter

    }
}
