package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.Data.DeadLineProcuctData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.DeadLineProductRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_dead_line_product.*
import kotlinx.android.synthetic.main.toolbar_all_product.*
import org.jetbrains.anko.startActivityForResult


class DeadLineProductActivity : AppCompatActivity() {

    val REQUEST_CODE_CATEGORY = 1000
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
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

        configToolBar()
    }

    fun configToolBar(){
        btn_filter_toolbar_all_product.setOnClickListener {
            startActivityForResult<CategoryActivity>(REQUEST_CODE_CATEGORY,"requestCode" to REQUEST_CODE_CATEGORY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //필터
        if (requestCode == REQUEST_CODE_CATEGORY) {
            if (resultCode == Activity.RESULT_OK) {
                val areaList = data!!.getStringArrayListExtra("areaList")
                val ageList = data!!.getStringArrayListExtra("ageList")
                val categoryList = data!!.getStringArrayListExtra("categoryList")

                var dataList : ArrayList<CategoryData> = ArrayList()

                for(i in 0..areaList.size-1){
                    dataList.add(CategoryData(areaList[i]))
                }
                for(i in 0..ageList.size-1){
                    dataList.add(CategoryData(ageList[i]))
                }
                for(i in 0..categoryList.size-1){
                    dataList.add(CategoryData(categoryList[i]))
                }

                categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(this, dataList)
                rv_filter_deadline_product.adapter = categoryRecyclerViewAdapter
                rv_filter_deadline_product.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

                rv_filter_deadline_product.visibility = View.VISIBLE
            }
        }
    }
}
