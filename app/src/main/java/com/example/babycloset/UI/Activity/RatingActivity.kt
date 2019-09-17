package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostRatingResponse
import com.example.babycloset.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_rating.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingActivity : AppCompatActivity() {

    var receiverIdx: Int = -1
    var senderIdx:Int =-1
    var request_code:Int=-1

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        request_code=intent.getIntExtra("REQUESTCODE",-1)
        if(request_code==-1) finish()
        else if(request_code==100){
            receiverIdx = intent.getIntExtra("receiverIdx", -1)
            if (receiverIdx == -1) finish()

            var receiverNickname = intent.getStringExtra("receiverNickname")
            txt_share_name.text = receiverNickname
            txt_share_product.text = intent.getStringExtra("postName")

            btn_rating.setOnClickListener {
                val u_rating:Int = ratingStar.rating.toInt()
                val u_userIdx:Int = receiverIdx
                val u_postIdx:Int = intent.getIntExtra("postIdx",-1)
                postRatingResponse(u_rating, u_userIdx,u_postIdx)
            }
        }
        else if(request_code==200){
            senderIdx = intent.getIntExtra("senderIdx", -1)
            if (senderIdx == -1) finish()

            var senderNickname = intent.getStringExtra("senderNickname")
            txt_share_name.text = senderNickname
            txt_share_product.text = intent.getStringExtra("postName")

            btn_rating.setOnClickListener {
                val u_rating:Int = ratingStar.rating.toInt()
                val u_userIdx:Int = senderIdx
                val u_postIdx:Int = intent.getIntExtra("postIdx",-1)
                postRatingResponse(u_rating, u_userIdx,u_postIdx)
            }
        }

        // 5개의 별!
        ratingStar.numStars=5
        // 별점주기 버튼에 post
        // 액티비티 종료 버튼
        btn_close.setOnClickListener {
            finish()
        }
    }

    fun postRatingResponse(u_rate: Int, u_idx: Int, u_postIdx:Int){
        var jsonObject = JSONObject()
        jsonObject.put("userIdx", u_idx)
        jsonObject.put("postIdx", u_postIdx)
        jsonObject.put("rating", u_rate)

        val token = SharedPreference.getUserToken(ctx)
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postRatingResponse: Call<PostRatingResponse> =
            networkService.postRatingResponse("application/json",token, gsonObject)

        postRatingResponse.enqueue(object : Callback<PostRatingResponse> {
            override fun onFailure(call: Call<PostRatingResponse>, t: Throwable) {
               // toast("Login failed")
            }

            override fun onResponse(call: Call<PostRatingResponse>, response: Response<PostRatingResponse>) {
                if (response.isSuccessful){
                    if (response.body()!!.status == 200){
                        finish()
                    }
                }
            }
        })
    }
}
