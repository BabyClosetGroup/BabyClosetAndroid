package com.example.babycloset.UI.Activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostLoginResponse
import com.example.babycloset.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //회원가입
        btn_to_signin.setOnClickListener {
            startActivity<InfoConsentActivity>()
        }

        layout_auto.setOnClickListener {
            if(btn_auto.isSelected==true){
                btn_auto.isSelected=false
                SharedPreference.setAutoLogin(this,!btn_auto.isSelected)
                txt_auto.setTextColor(Color.parseColor("#7d7d7d"))
            } else {
                btn_auto.isSelected=true
                SharedPreference.setAutoLogin(this,btn_auto.isSelected)
                txt_auto.setTextColor(Color.parseColor("#ffc107"))
            }
        }

        if(SharedPreference.getAutoLogin(this)==true){
            btn_auto.isSelected=true
            txt_auto.setTextColor(Color.parseColor("#ffc107"))
            if(SharedPreference.getUserID(this).isEmpty()){
                btn_login.setOnClickListener {
                    val login_u_id: String = edt_id_login.text.toString()
                    val login_u_pw: String = edt_pw_login.text.toString()

                    postLoginResponse(login_u_id, login_u_pw)
                }
            }else{
                edt_id_login.setText(SharedPreference.getUserID(this))
                edt_pw_login.setText(SharedPreference.getUserPW(this))
                postLoginResponse(SharedPreference.getUserID(this), SharedPreference.getUserPW(this))
            }
        } else {
            btn_login.setOnClickListener {
                val login_u_id: String = edt_id_login.text.toString()
                val login_u_pw: String = edt_pw_login.text.toString()

                postLoginResponse(login_u_id, login_u_pw)
            }
        }
    }

    override fun onPause() {
        super.onPause()

    }

    fun postLoginResponse(u_id: String, u_pw: String){
        var jsonObject = JSONObject()
        jsonObject.put("userId", u_id)
        jsonObject.put("password", u_pw)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postLoginResponse: Call<PostLoginResponse> =
            networkService.postLoginResponse("application/json", gsonObject)

        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                toast("Login failed")
            }

            override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                if (response.isSuccessful){
                    if (response.body()!!.status == 200){
                        SharedPreference.setUserToken(this@LoginActivity, response.body()!!.data!!.token)
                        SharedPreference.setUserID(this@LoginActivity,u_id)
                        SharedPreference.setUserPW(this@LoginActivity, u_pw)
                        Log.e("tag", response.body()!!.data!!.token)
                        startActivity<MainActivity>()
                    } else
                        toast(response.body()!!.message)
                }
            }
        })
    }
}
