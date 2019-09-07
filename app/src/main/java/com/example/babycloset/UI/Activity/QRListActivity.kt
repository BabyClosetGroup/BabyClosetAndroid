package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.babycloset.Data.QRListData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.QRListRecyclerAdapter
import kotlinx.android.synthetic.main.activity_qrlist.*

class QRListActivity : AppCompatActivity() {
    lateinit var qrListRecyclerAdapter: QRListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrlist)

        configList()
    }

    private fun configList(){
        var dataList: ArrayList<QRListData> = ArrayList()

        dataList.add(QRListData(0,"점프수트","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","중랑구"))
        dataList.add(QRListData(0,"여아 투피스","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"))
        dataList.add(QRListData(0,"분홍색 치마","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","영등포구"))

        qrListRecyclerAdapter= QRListRecyclerAdapter(this,dataList)
        rv_qr_list.adapter=qrListRecyclerAdapter
        rv_qr_list.layoutManager=LinearLayoutManager(this,LinearLayout.VERTICAL,false)
    }

}
