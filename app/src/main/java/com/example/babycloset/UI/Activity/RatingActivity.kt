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

    var recieverIdx: Int = -1

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        recieverIdx = intent.getIntExtra("recieverIdx", -1)
        if (recieverIdx == -1) finish()

        var recieverNickname = intent.getStringExtra("recieverNickname")
        txt_share_name.text = recieverNickname
        txt_share_product.text = "흠"

        // 5개의 별!
        ratingStar.numStars=5
        // 별점주기 버튼에 post
        btn_rating.setOnClickListener {
            val u_rating:Int = ratingStar.numStars
            val u_userIdx:Int = recieverIdx

            postRatingResponse(u_rating, u_userIdx)
        }
        // 액티비티 종료 버튼
        btn_close.setOnClickListener {
            finish()
        }
    }
    fun postRatingResponse(u_rate: Int, u_idx: Int){
        var jsonObject = JSONObject()
        jsonObject.put("userIdx", u_idx)
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
                        startActivity<ReceiveProductActivity>()
                    }
                }
            }

        })

    }

}
