package com.example.babycloset.UI.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.QRListData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetQRListResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.QRListRecyclerAdapter
import kotlinx.android.synthetic.main.activity_qrlist.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QRListActivity : AppCompatActivity() {
    lateinit var qrListRecyclerAdapter: QRListRecyclerAdapter
    var qrListDataList: ArrayList<QRListData> = ArrayList()


    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrlist)

        configList()

        //통신
        getQRListResponse()
    }

    private fun configList(){

//        dataList.add(QRListData(0,"점프수트","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","중랑구"))
//        dataList.add(QRListData(0,"여아 투피스","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","동대문구"))
//        dataList.add(QRListData(0,"분홍색 치마","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg","영등포구"))


        qrListRecyclerAdapter= QRListRecyclerAdapter(this,qrListDataList)
        rv_qr_list.adapter=qrListRecyclerAdapter
        rv_qr_list.layoutManager=LinearLayoutManager(this,LinearLayout.VERTICAL,false)

    }

    private fun getQRListResponse(){
        val token: String = SharedPreference.getUserToken(this)
        val getQRListResponse=networkService.getQRListResponse("application/json",token)
        getQRListResponse.enqueue(object: Callback<GetQRListResponse>{
            override fun onFailure(call: Call<GetQRListResponse>, t: Throwable) {
                Log.e("qrlist fail",t.toString())
            }

            override fun onResponse(call: Call<GetQRListResponse>, response: Response<GetQRListResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status==200){
                        Log.e("qrlist success","성공")
                        qrListDataList.clear()

                        var tmp=response.body()!!.data.allPost

                        qrListRecyclerAdapter.dataList!!.addAll(tmp)
                        qrListRecyclerAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

}
