package com.example.babycloset.UI.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.babycloset.Data.CompleteProductOverviewData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CompleteProductOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_share_complete.*

class ShareCompleteFragment : Fragment() {
    lateinit var completeProductOverviewRecyclerViewAdapter: CompleteProductOverviewRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_complete, container, false)

        //상품정보 가져오기
        //별점주기 누르면 창이동, 별점 부여시 버튼 사라지게
        //정보보기 누르면 상대 평점점
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var dataList: ArrayList<CompleteProductOverviewData> = ArrayList()
        dataList.add(CompleteProductOverviewData(
            "우주복", "동대문구","2019.09.01","박지윤","미부여"))
        dataList.add(CompleteProductOverviewData(
            "한복", "관악구","2019.09.03","신초희","4"))

        completeProductOverviewRecyclerViewAdapter = CompleteProductOverviewRecyclerViewAdapter(context!!, dataList)
        rv_complete_product_overview.adapter = completeProductOverviewRecyclerViewAdapter
        rv_complete_product_overview.layoutManager = LinearLayoutManager(context!!)
    }
}
