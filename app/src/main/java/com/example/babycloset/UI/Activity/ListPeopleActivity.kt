package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.babycloset.Data.ApplicationPeopleOverviewData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.ApplicationPeopleOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_list_people.*

class ListPeopleActivity : AppCompatActivity() {

    lateinit var applicationPeopleOverviewRecyclerViewAdapter: ApplicationPeopleOverviewRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_people)

        //상품 데이터 가져오기
        //신청자 데이터 가져오기
        //버튼 클릭시 쪽지 페이지로

        configureRecyclerView()
    }
    private fun configureRecyclerView() {
        var dataList: ArrayList<ApplicationPeopleOverviewData> = ArrayList()
        /*dataList.add(ApplicationPeopleOverviewData(
            "박지윤", "5"))*/
        dataList.add(ApplicationPeopleOverviewData(
            4, "정미",4))

        applicationPeopleOverviewRecyclerViewAdapter = ApplicationPeopleOverviewRecyclerViewAdapter(this, dataList)
        rv_application_people_overview.adapter = applicationPeopleOverviewRecyclerViewAdapter
        rv_application_people_overview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}
