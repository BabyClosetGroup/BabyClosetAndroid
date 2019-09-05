package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.babycloset.Data.DeadLineProcuctData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.DeadLineProductRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_dead_line_product.*
import kotlinx.android.synthetic.main.toolbar_all_product.*


class DeadLineProductActivity : AppCompatActivity() {

    lateinit var deadLineProductRecyclerViewAdapter: DeadLineProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dead_line_product)

        txt_title_all_product.text = "마감임박"

        var dataList : ArrayList<DeadLineProcuctData> = ArrayList()
        dataList.add(DeadLineProcuctData(27, "귀여운 하얀색 상의 옷", "D-2",
            "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","서초구"))

        dataList.add(DeadLineProcuctData(26,"귀여운 파란색 상의 옷","D-2",
            "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341854422.jpeg", "송파구"))
        dataList.add(DeadLineProcuctData(24,"귀여운 주황색 상의 옷","D-0",
            "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341765984.jpeg", "송파구" ))
        dataList.add(
            DeadLineProcuctData(23,"상큼한 초록색 상의 옷","D-1",
                "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567255151131.jpeg", "서대문구"))

        dataList.add(
            DeadLineProcuctData(21, "상큼한 하얀색 상의 옷","D-1",
                "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567255151131.jpeg", "송파구"))
        dataList.add(
            DeadLineProcuctData(20,"상큼한 노란색 상의 옷","D-2",
                "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567255151131.jpeg", "동작구" ))



        deadLineProductRecyclerViewAdapter = DeadLineProductRecyclerViewAdapter(this,dataList)
        rv_item_deadline_product.adapter = deadLineProductRecyclerViewAdapter
        rv_item_deadline_product.layoutManager = GridLayoutManager(this,2)

    }
}
