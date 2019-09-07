package com.example.babycloset.UI.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.Data.HomeDeadlineData
import com.example.babycloset.Data.HomeRecentData

import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.HomeDeadlineRecyclerAdapter
import com.example.babycloset.UI.Adapter.HomeRecentRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    lateinit var homeDeadlineRecyclerAdapter: HomeDeadlineRecyclerAdapter
    lateinit var homeRecentRecyclerAdapter: HomeRecentRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configDeadline()
        configRecent()
    }

    private fun configDeadline(){
        var dataList: ArrayList<HomeDeadlineData> = ArrayList()


        dataList.add(HomeDeadlineData(
            0,"하늘색 잠옷","D-0","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
        ))
        dataList.add(HomeDeadlineData(
            0,"하늘색 잠옷","D-0","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
        ))
        dataList.add(HomeDeadlineData(
            0,"하늘색 잠옷","D-0","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
        ))


        homeDeadlineRecyclerAdapter= HomeDeadlineRecyclerAdapter(context!!,dataList)
        rv_item_deadline_all.adapter = homeDeadlineRecyclerAdapter
        rv_item_deadline_all.layoutManager = GridLayoutManager(context!!,3)
    }

    private fun configRecent(){
        var dataList: ArrayList<HomeRecentData> = ArrayList()


        dataList.add(HomeRecentData(
            0,"하늘색 잠옷","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
        ))
        dataList.add(HomeRecentData(
            0,"하늘색 잠옷","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
        ))
        dataList.add(HomeRecentData(
            0,"하늘색 잠옷","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
        ))
        dataList.add(HomeRecentData(
            0,"하늘색 잠옷","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"
        ))


        homeRecentRecyclerAdapter= HomeRecentRecyclerAdapter(context!!,dataList)
        rv_item_recent_all.adapter = homeRecentRecyclerAdapter
        rv_item_recent_all.layoutManager = GridLayoutManager(context!!,2)
    }
}
