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
import com.example.babycloset.Network.Get.Getproductdata
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.ApplicationPeopleOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_list_people.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Response

class ListPeopleActivity : AppCompatActivity() {

    var postIdx: Int = -1

    lateinit var applicationPeopleOverviewRecyclerViewAdapter: ApplicationPeopleOverviewRecyclerViewAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
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

        /*dataList.add(ApplicationPeopleOverviewData(
            1,"지윤",3,null
        ))*/
        applicationPeopleOverviewRecyclerViewAdapter = ApplicationPeopleOverviewRecyclerViewAdapter(this, dataList)
        rv_application_people_overview.adapter = applicationPeopleOverviewRecyclerViewAdapter
        rv_application_people_overview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        getListPeopleResponse()
    }

    private fun getListPeopleResponse(){
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJuaWNrbmFtZSI6IuyEne2ZqSIsImlhdCI6MTU2ODIxNzMyNCwiZXhwIjoxNTc5MDE3MzI0LCJpc3MiOiJiYWJ5Q2xvc2V0In0.pGluiC04m2sXWdtHwWKR8SdSMQYS_kSd_uumifKBz18"

        //val token = SharedPreference.getUserToken(ctx)

        val getListPeopleResponse = networkService.getlistpeopleResponse("application/json", token, 17)
        getListPeopleResponse.enqueue(object : retrofit2.Callback<GetListPeopleResponse>{
            override fun onFailure(call: Call<GetListPeopleResponse>, t: Throwable) {
                Log.e("tag", "실패!")
                t.printStackTrace()
            }
            override fun onResponse(call: Call<GetListPeopleResponse>, response: Response<GetListPeopleResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        var tmp1: Getproductdata = response.body()!!.data.post!!
                        txt_product.text = tmp1.postTitle
                        var locList:ArrayList<String> = tmp1.areaName
                        if(locList.size-1!=0){
                            txt_location.text = locList[0]
                            txt_location_extra.text ="외 "+(locList.size-1)+"구"
                        } else{
                            txt_location.text = locList[0]
                            txt_location_extra.text ="" }
                        txt_number.text = tmp1.applicantNumber
                        var x = tmp1.applicantNumber
                        var item: Char
                        item = x[0]
                        txt_application_num.text="("+item+")"
                        Glide.with(ctx).load(tmp1.mainImage).into(img_thumbnail)

                        var tmp: ArrayList<ApplicationPeopleOverviewData> = response.body()!!.data.applicants!!
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

}
