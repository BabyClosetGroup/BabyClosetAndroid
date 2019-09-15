package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.babycloset.Data.EmailData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.EmailRecyclerAdapter
import kotlinx.android.synthetic.main.activity_email.*

class EmailActivity : AppCompatActivity() {
    lateinit var emailRecyclerAdapter: EmailRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        configureList()

    }

    private fun configureList(){
        var dataList: ArrayList<EmailData> = ArrayList()

        dataList.add(EmailData(0,"현아는베베","19/01/12 15:51","네네 알겠습니다", "+1"))
        dataList.add(EmailData(1,"정미","19/01/12 15:51","3시 어떠신가요?", "+3"))

        emailRecyclerAdapter= EmailRecyclerAdapter(this,dataList)
        rv_email.adapter=emailRecyclerAdapter
        rv_email.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false)
    }

}
