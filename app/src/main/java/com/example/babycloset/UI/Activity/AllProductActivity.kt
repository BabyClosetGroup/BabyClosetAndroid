package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.babycloset.Data.AllProductData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.AllProductRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_all_product.*


class AllProductActivity : AppCompatActivity() {

    lateinit var allProductRecyclerViewAdapter: AllProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_product)

        var dataList : ArrayList<AllProductData> = ArrayList()
        dataList.add(AllProductData(27, "귀여운 하얀색 상의 옷",
            "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","서초구"))

        dataList.add(AllProductData(26,"귀여운 파란색 상의 옷",
            "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341854422.jpeg", "송파구"))
        dataList.add(AllProductData(24,"귀여운 주황색 상의 옷",
            "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341765984.jpeg", "송파구" ))
        dataList.add(
            AllProductData(23,"상큼한 초록색 상의 옷",
                "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567255151131.jpeg", "서대문구"))

        dataList.add(
            AllProductData(21, "상큼한 하얀색 상의 옷",
                 "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567255151131.jpeg", "송파구"))
        dataList.add(
            AllProductData(20,"상큼한 노란색 상의 옷",
                 "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567255151131.jpeg", "동작구" ))

        allProductRecyclerViewAdapter = AllProductRecyclerViewAdapter(this, dataList)
        rv_item_all_product.adapter = allProductRecyclerViewAdapter
        rv_item_all_product.layoutManager = GridLayoutManager(this, 2)

    }
}
