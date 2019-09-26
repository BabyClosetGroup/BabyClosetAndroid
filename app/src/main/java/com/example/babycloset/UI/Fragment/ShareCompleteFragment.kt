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
import android.widget.LinearLayout
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.CompleteProductOverviewData
import com.example.babycloset.Network.Get.GetShareCompleteResponse
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CompleteProductOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_share_complete.*
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Response

class ShareCompleteFragment : Fragment() {
    lateinit var completeProductOverviewRecyclerViewAdapter: CompleteProductOverviewRecyclerViewAdapter

    var dataList: ArrayList<CompleteProductOverviewData> = ArrayList()

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
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


        completeProductOverviewRecyclerViewAdapter = CompleteProductOverviewRecyclerViewAdapter(context!!, dataList)
        rv_complete_product_overview.adapter = completeProductOverviewRecyclerViewAdapter
        rv_complete_product_overview.layoutManager = LinearLayoutManager(context!!)

        getShareCompleteResponse()
    }

    private fun getShareCompleteResponse(){
        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJuaWNrbmFtZSI6IuyEne2ZqSIsImlhdCI6MTU2ODIxNzMyNCwiZXhwIjoxNTc5MDE3MzI0LCJpc3MiOiJiYWJ5Q2xvc2V0In0.pGluiC04m2sXWdtHwWKR8SdSMQYS_kSd_uumifKBz18"

        val getShareCompleteResponse = networkService.getsharecompleteResponse("application/json", token)
        getShareCompleteResponse.enqueue(object : retrofit2.Callback<GetShareCompleteResponse>{
            override fun onFailure(call: Call<GetShareCompleteResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<GetShareCompleteResponse>,
                response: Response<GetShareCompleteResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        val tmp: ArrayList<CompleteProductOverviewData> = response.body()!!.data.allPost
                        completeProductOverviewRecyclerViewAdapter.dataList = tmp
                        completeProductOverviewRecyclerViewAdapter.notifyDataSetChanged()
                        if(completeProductOverviewRecyclerViewAdapter.itemCount>0)
                            complete_empty.setVisibility(View.GONE);
                        Log.e("tag", "성공")
                    }
                    else if (response.body()!!.status == 400){
                        Log.e("tag", "No token")
                    }
                }
            }
        })

    }


}
