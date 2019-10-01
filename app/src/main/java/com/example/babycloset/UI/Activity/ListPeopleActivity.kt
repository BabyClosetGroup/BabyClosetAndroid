package com.example.babycloset.UI.Activity

import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.ApplicantOverviewData
import com.example.babycloset.Network.Get.GetListPeopleResponse
import com.example.babycloset.Network.Get.Getproductdata
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.ApplicantOverviewRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_list_people.*
import org.jetbrains.anko.ctx
import retrofit2.Call
import retrofit2.Response

class ListPeopleActivity : AppCompatActivity() {

    var postIdx: Int = -1

    lateinit var applicantOverviewRecyclerViewAdapter: ApplicantOverviewRecyclerViewAdapter

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_people)

        postIdx = intent.getIntExtra("postIdx", -1)
        if (postIdx == -1) finish()
        configureRecyclerView()
    }
    private fun configureRecyclerView() {
        var dataList: ArrayList<ApplicantOverviewData> = ArrayList()

        /*dataList.add(ApplicantOverviewData(
            1,"test",3f,null
        ))*/
        applicantOverviewRecyclerViewAdapter = ApplicantOverviewRecyclerViewAdapter(this, dataList)
        rv_applicant_overview.adapter = applicantOverviewRecyclerViewAdapter
        rv_applicant_overview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        getListPeopleResponse()
    }

    private fun getListPeopleResponse(){
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJuaWNrbmFtZSI6IuyEne2ZqSIsImlhdCI6MTU2ODIxNzMyNCwiZXhwIjoxNTc5MDE3MzI0LCJpc3MiOiJiYWJ5Q2xvc2V0In0.pGluiC04m2sXWdtHwWKR8SdSMQYS_kSd_uumifKBz18"
        val token = SharedPreference.getUserToken(ctx)

        val getListPeopleResponse = networkService.getlistpeopleResponse("application/json", token, postIdx)
        getListPeopleResponse.enqueue(object : retrofit2.Callback<GetListPeopleResponse>{
            override fun onFailure(call: Call<GetListPeopleResponse>, t: Throwable) {
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

                        val drawable: GradientDrawable = ctx.getDrawable(R.drawable.img_background_rounding) as GradientDrawable
                        img_thumbnail.background = drawable
                        img_thumbnail.clipToOutline = true

                        Glide.with(ctx).load(tmp1.mainImage).into(img_thumbnail)

                        var tmp: ArrayList<ApplicantOverviewData> = response.body()!!.data.applicants!!
                        applicantOverviewRecyclerViewAdapter.dataList = tmp
                        applicantOverviewRecyclerViewAdapter.notifyDataSetChanged()
                    }
                    else if (response.body()!!.status == 400){
                        Log.e("tag", "No token")
                    }
                }
            }
        })

    }

}
