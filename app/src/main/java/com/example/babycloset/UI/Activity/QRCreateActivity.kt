package com.example.babycloset.UI.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetQRCreateResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_qrcreate.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QRCreateActivity : AppCompatActivity() {
    val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"
    var postindex = -1

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcreate)

        var intent: Intent = intent
        postindex=intent.getIntExtra("postindex",-1)

        getCreateResponse()
    }

    private fun getCreateResponse(){
        val getQRCreateResponse=networkService.getQRCreateResponse("application/json",token,postindex)
        getQRCreateResponse.enqueue(object: Callback<GetQRCreateResponse>{
            override fun onFailure(call: Call<GetQRCreateResponse>, t: Throwable) {
                Log.e("qrCreate fail",t.toString())
            }

            override fun onResponse(call: Call<GetQRCreateResponse>, response: Response<GetQRCreateResponse>) {
                if(response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        txt_qr_create_title.text=response.body()!!.data.postTitle
                        Glide.with(this@QRCreateActivity).load(response.body()!!.data.qrcode).into(img_create_qr_code)
                    }
                }
            }
        })

    }
}
