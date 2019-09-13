package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.babycloset.Data.EmailPersonData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.EmailPersonRecyclerAdapter
import kotlinx.android.synthetic.main.activity_email.*
import kotlinx.android.synthetic.main.rv_email_people.*
import org.jetbrains.anko.startActivity

class EmailActivity : AppCompatActivity() {
    lateinit var emailPersonRecyclerAdapter: EmailPersonRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        configList()

        rv_email_people.setOnClickListener {
            //startActivity<EmailPersonActivity>() //아직 안넘어감...
        }
    }

    private fun configList(){
        var dataList: ArrayList<EmailPersonData> = ArrayList()

        dataList.add(EmailPersonData(0,"현아는베베","19/01/12 15:51","네네 알겠습니다", "+1"))
        dataList.add(EmailPersonData(1,"정미","19/01/12 15:51","3시 어떠신가요?", "+3"))

        emailPersonRecyclerAdapter= EmailPersonRecyclerAdapter(this,dataList)
        rv_email_people.adapter=emailPersonRecyclerAdapter
        rv_email_people.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false)
    }

}
