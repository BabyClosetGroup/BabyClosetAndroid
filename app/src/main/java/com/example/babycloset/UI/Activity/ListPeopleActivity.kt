package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.ApplicationPeopleOverviewData
import com.example.babycloset.Data.IncompleteProductOverviewData
import com.example.babycloset.Network.Get.GetListPeopleResponse
import com.example.babycloset.Network.Get.GetShareIncompleteResponse
import com.example.babycloset.Network.Get.Getproductdata/*
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService*/
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.ApplicationPeopleOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_list_people.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.ctx
import retrofit2.Call
import retrofit2.Response

class ListPeopleActivity : AppCompatActivity() {

    var postIdx: Int = -1

    lateinit var applicationPeopleOverviewRecyclerViewAdapter: ApplicationPeopleOverviewRecyclerViewAdapter

    /*val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_people)

        //상품 데이터 가져오기
        //신청자 데이터 가져오기
        //버튼 클릭시 쪽지 페이지로

        postIdx = intent.getIntExtra("postIdx", -1)
        if (postIdx == -1) finish()

        configureRecyclerView()
    }
    private fun configureRecyclerView() {
        var dataList: ArrayList<ApplicationPeopleOverviewData> = ArrayList()

        applicationPeopleOverviewRecyclerViewAdapter = ApplicationPeopleOverviewRecyclerViewAdapter(this, dataList)
        rv_application_people_overview.adapter = applicationPeopleOverviewRecyclerViewAdapter
        rv_application_people_overview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //getListPeopleResponse()
    }
/*
    private fun getListPeopleResponse(){

        val token = SharedPreference.getUserToken(ctx)

        val getListPeopleResponse = networkService.getlistpeopleResponse("application/json", token, postIdx)
        getListPeopleResponse.enqueue(object : retrofit2.Callback<GetListPeopleResponse>{
            override fun onFailure(call: Call<GetListPeopleResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<GetListPeopleResponse>,
                response: Response<GetListPeopleResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        var tmp1: ArrayList<Getproductdata> = response.body()!!.post!!
                        txt_product.setText(tmp1[0].postTitle)
                        txt_location.text = tmp1[0].areaName
                        txt_number.text = tmp1[0].applicantNumber

                        Glide.with(ctx).load(tmp1[0].mainImage).into(img_thumbnail)

                        val tmp: ArrayList<ApplicationPeopleOverviewData> = response.body()!!.data!!
                        applicationPeopleOverviewRecyclerViewAdapter.dataList = tmp
                        applicationPeopleOverviewRecyclerViewAdapter.notifyDataSetChanged()

                        Log.e("tag", "성공")
                    }
                    else if (response.body()!!.status == 400){
                        Log.e("tag", "No token")
                    }
                }
            }
        })

    }
*/
}
