package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.ReceiveProductOverviewData
import com.example.babycloset.Network.Get.GetReceiveProductResponse
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.ReceiveProductOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_receive_product.*
import org.jetbrains.anko.ctx
import retrofit2.Call
import retrofit2.Response

class ReceiveProductActivity : AppCompatActivity() {
    lateinit var receiveProductOverviewRecyclerViewAdapter: ReceiveProductOverviewRecyclerViewAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_product)

        //상품정보 불러오기
        //별점이 있으면 버튼 없어지게
        //별점주기 누르면 별점액티비티이동
        //정보보기 누르면 상대방의 평점팝업

        var dataList: ArrayList<ReceiveProductOverviewData> = ArrayList()

        receiveProductOverviewRecyclerViewAdapter = ReceiveProductOverviewRecyclerViewAdapter(this, dataList)
        rv_receive_product_overview.adapter = receiveProductOverviewRecyclerViewAdapter
        rv_receive_product_overview.layoutManager = LinearLayoutManager(this)

        getReceiveProductResponse()
    }

    private fun getReceiveProductResponse(){

        val token = SharedPreference.getUserToken(ctx)
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"
        val getReceiveProductResponse = networkService.getreceiveproductResponse("application/json", token)
        getReceiveProductResponse.enqueue(object : retrofit2.Callback<GetReceiveProductResponse>{
            override fun onFailure(call: Call<GetReceiveProductResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<GetReceiveProductResponse>,
                response: Response<GetReceiveProductResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        val tmp: ArrayList<ReceiveProductOverviewData> = response.body()!!.data.allPost
                        receiveProductOverviewRecyclerViewAdapter.dataList = tmp
                        receiveProductOverviewRecyclerViewAdapter.notifyDataSetChanged()
                        if(receiveProductOverviewRecyclerViewAdapter.itemCount>0)
                            receive_empty.setVisibility(View.GONE);

                    }
                    else if (response.body()!!.status == 400){
                        Log.e("tag", "No token")
                    }
                }
            }
        })

    }


}
