package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.EmailData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetEmailResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.EmailRecyclerAdapter
import kotlinx.android.synthetic.main.activity_email.*
import kotlinx.android.synthetic.main.rv_email.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailActivity : AppCompatActivity() {
    lateinit var emailRecyclerAdapter: EmailRecyclerAdapter

    val networkService: NetworkService by lazy {
     ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        var dataList: ArrayList<EmailData> = ArrayList()

        emailRecyclerAdapter = EmailRecyclerAdapter(this, dataList)
        rv_email.adapter = emailRecyclerAdapter
        rv_email.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)

        getEmailResponse()



    }
/*
    private fun configureList(){
        var dataList: ArrayList<EmailData> = ArrayList()

        //dataList.add(EmailData(0,"현아는베베","19/01/12 15:51","네네 알겠습니다", "0"))
        //dataList.add(EmailData(1,"정미","19/01/12 15:51","3시 어떠신가요?","3"))


//        if(dataList.size == 0) { //디폴트 글자 나오는 경우
//            rv_email.visibility = GONE
//            ll_text_email_default.visibility = VISIBLE
//        }


        emailRecyclerAdapter = EmailRecyclerAdapter(this, dataList)
        rv_email.adapter = emailRecyclerAdapter
        rv_email.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)

        getEmailResponse()
    }*/

    private fun getEmailResponse(){
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxLCJuaWNrbmFtZSI6IuyEne2ZqSIsImlhdCI6MTU2ODIxNzMyNCwiZXhwIjoxNTc5MDE3MzI0LCJpc3MiOiJiYWJ5Q2xvc2V0In0.pGluiC04m2sXWdtHwWKR8SdSMQYS_kSd_uumifKBz18"
        //val token = SharedPreference.getUserToken(this)
        val getEmailResponse = networkService.getEmailResponse("application/json", token)

        getEmailResponse.enqueue(object : Callback<GetEmailResponse>{
            override fun onFailure(call: Call<GetEmailResponse>, t: Throwable) {
            }
            override fun onResponse(call: Call<GetEmailResponse>, response: Response<GetEmailResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        val tmp: ArrayList<EmailData> = response.body()!!.data.getNotes
                        emailRecyclerAdapter.dataList = tmp
                        emailRecyclerAdapter.notifyDataSetChanged()
                        //Log.e("tag", "성공")
                        if(emailRecyclerAdapter.dataList.size == 0) { //디폴트 글자 나오는 경우
                            rv_email.visibility = GONE
                            ll_text_email_default.visibility = VISIBLE
                        }
                    }
                    else if (response.body()!!.status == 400){
                        //Log.e("tag", "No token")
                    }
                }
            }
        })
    }

}
