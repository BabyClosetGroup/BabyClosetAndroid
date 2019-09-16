package com.example.babycloset.UI.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.CompleteProductOverviewData
import com.example.babycloset.Data.IncompleteProductOverviewData
import com.example.babycloset.Network.Get.GetShareIncompleteResponse
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CompleteProductOverviewRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.IncompleteProductOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_share_complete.*
import kotlinx.android.synthetic.main.fragment_share_incomplete.*
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Response

class ShareIncompleteFragment : Fragment() {

    lateinit var incompleteProductOverviewRecyclerViewAdapter: IncompleteProductOverviewRecyclerViewAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

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

            incompleteProductOverviewRecyclerViewAdapter = IncompleteProductOverviewRecyclerViewAdapter(context!!, dataList)
            rv_incomplete_product_overview.adapter = incompleteProductOverviewRecyclerViewAdapter
            rv_incomplete_product_overview.layoutManager = LinearLayoutManager(context!!)

            if (dataList.isEmpty()) {
                rv_incomplete_product_overview.setVisibility(View.GONE);
                incomplete_empty.setVisibility(View.VISIBLE);
            }
            else {
                rv_incomplete_product_overview.setVisibility(View.VISIBLE);
                incomplete_empty.setVisibility(View.GONE);
            }

            getShareIncompleteResponse()
    }
    private fun getShareIncompleteResponse(){

/*
        val token = SharedPreference.getUserToken(ctx)
*/
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJuaWNrbmFtZSI6IuyEne2ZqSIsImlhdCI6MTU2ODIxNzMyNCwiZXhwIjoxNTc5MDE3MzI0LCJpc3MiOiJiYWJ5Q2xvc2V0In0.pGluiC04m2sXWdtHwWKR8SdSMQYS_kSd_uumifKBz18"

        val getShareIncompleteResponse = networkService.getshareIncompleteResponse("application/json", token)
        getShareIncompleteResponse.enqueue(object : retrofit2.Callback<GetShareIncompleteResponse>{
            override fun onFailure(call: Call<GetShareIncompleteResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<GetShareIncompleteResponse>,
                response: Response<GetShareIncompleteResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        val tmp: ArrayList<IncompleteProductOverviewData> = response.body()!!.data!!
                        incompleteProductOverviewRecyclerViewAdapter.dataList = tmp
                        incompleteProductOverviewRecyclerViewAdapter.notifyDataSetChanged()

                        Log.e("tag", "포폴리스트 성공")
                    }
                    else if (response.body()!!.status == 400){
                        Log.e("tag", "No token")
                    }
                }
            }
        })

    }

}
