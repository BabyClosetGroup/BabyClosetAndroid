package com.example.babycloset.UI.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.Data.CompleteProductOverviewData
import com.example.babycloset.Data.IncompleteProductOverviewData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CompleteProductOverviewRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.IncompleteProductOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_share_complete.*
import kotlinx.android.synthetic.main.fragment_share_incomplete.*

class ShareIncompleteFragment : Fragment() {

    lateinit var incompleteProductOverviewRecyclerViewAdapter: IncompleteProductOverviewRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_incomplete, container, false)


        //상품정보 가져오기
        //나눔하기 누르면 신청자목록으로
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            var dataList: ArrayList<IncompleteProductOverviewData> = ArrayList()
            dataList.add(
                IncompleteProductOverviewData(
                    27, "https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","서초구","3명")
            )

            incompleteProductOverviewRecyclerViewAdapter = IncompleteProductOverviewRecyclerViewAdapter(context!!, dataList)
            rv_incomplete_product_overview.adapter = incompleteProductOverviewRecyclerViewAdapter
            rv_incomplete_product_overview.layoutManager = LinearLayoutManager(context!!)
    }
}
