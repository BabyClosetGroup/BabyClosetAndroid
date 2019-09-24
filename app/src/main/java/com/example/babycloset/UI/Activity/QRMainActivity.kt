package com.example.babycloset.UI.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostQRcodeResponse
import com.example.babycloset.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_qrmain.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QRMainActivity : AppCompatActivity() {
    lateinit var qrscan: IntentIntegrator
    val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"


    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrmain)

        //큐알 생성
        btn_qr_create.setOnClickListener{
            startActivity<QRListActivity>("id" to 1)
        }
        view_qr_create.setOnClickListener{
            startActivity<QRListActivity>("id" to 1)
        }

        //큐알 스캔
        btn_qr_scan.setOnClickListener{
            qrscan()
        }
        view_qr_scan.setOnClickListener{
            qrscan()
        }
    }
    private fun qrscan(){
        qrscan=IntentIntegrator(this)
        qrscan.setOrientationLocked(false)
        qrscan.setPrompt("인식이 잘 안된다면 코드가 있는\n 휴대폰의 화면 밝기를 높혀 주세요.")
        qrscan.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result: IntentResult =IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result!=null){
            if(result.contents==null){
                toast("Cancelled")
            }else{
                var jsonObject=JSONObject()
                jsonObject.put("decode",result.contents)

                val gsonObject= JsonParser().parse(jsonObject.toString()) as JsonObject
                val postQRcodeResponse=networkService.postQRcodeResponse("application/json",token,gsonObject)
                postQRcodeResponse.enqueue(object: Callback<PostQRcodeResponse>{
                    override fun onFailure(call: Call<PostQRcodeResponse>, t: Throwable) {
                        Log.e("qrscan fail",t.toString())
                    }

                    override fun onResponse(call: Call<PostQRcodeResponse>, response: Response<PostQRcodeResponse>) {
                        if(response.isSuccessful){
                            if(response.body()!!.status==200) {
                                toast("Scanned: "+result.contents)
                            }
                        }
                    }
                })
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
