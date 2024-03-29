package com.example.babycloset.UI.Activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostSignupResponse
import com.example.babycloset.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //아이디 한글 막기
        //비밀번호 6자이상, 한글막기
        //비밀번호 확인
        //버튼으로 아이디, 비번, 이름 전송
        btn_signin.isEnabled=false

        edt_name_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != ""
                    && edt_pw_siginin.text.toString().length>=6 && edt_nickname_signin.text.toString().length<8 && Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())
                    && Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString()) && Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                    btn_signin.isEnabled = true
                    btn_signin.setBackgroundColor(Color.parseColor("#ffc107"))
                    btn_signin.setOnClickListener {
                        val signin_u_id: String = edt_id_siginin.text.toString()
                        val signin_u_name: String = edt_name_siginin.text.toString()
                        val signin_u_nickname: String = edt_nickname_signin.text.toString()
                        val signin_u_pw: String = edt_pw_siginin.text.toString()
                        postSignupResponse(signin_u_id, signin_u_name, signin_u_nickname, signin_u_pw)
                    }
                }
                else {
                    btn_signin.isEnabled = false
                    btn_signin.setBackgroundColor(Color.parseColor("#767676"))
                }
            }
        })

        edt_id_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString())){
                    id_error.visibility= View.VISIBLE
                    edt_id_siginin.setBackgroundResource(R.drawable.signin_error_border)
                }
                else{
                    id_error.visibility=View.INVISIBLE
                    edt_id_siginin.setBackgroundResource(R.drawable.signin_enter_border)
                }
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != ""
                    && edt_pw_siginin.text.toString().length>=6 && edt_nickname_signin.text.toString().length<8&& Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())
                    && Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString()) && Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                    btn_signin.isEnabled = true
                    btn_signin.setBackgroundColor(Color.parseColor("#ffc107"))
                    btn_signin.setOnClickListener {
                        val signin_u_id: String = edt_id_siginin.text.toString()
                        val signin_u_name: String = edt_name_siginin.text.toString()
                        val signin_u_nickname: String = edt_nickname_signin.text.toString()
                        val signin_u_pw: String = edt_pw_siginin.text.toString()
                        postSignupResponse(signin_u_id, signin_u_name, signin_u_nickname, signin_u_pw)
                    }
                }
                else {
                    btn_signin.isEnabled = false
                    btn_signin.setBackgroundColor(Color.parseColor("#767676"))
                }
            }
        })

        edt_pw_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(edt_pw_siginin.text.toString().length<6||!Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())){
                    pw_error.visibility=View.VISIBLE
                    edt_pw_siginin.setBackgroundResource(R.drawable.signin_error_border)
                }
                else{
                    pw_error.visibility=View.INVISIBLE
                    edt_pw_siginin.setBackgroundResource(R.drawable.signin_enter_border)
                }
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != ""
                    && edt_pw_siginin.text.toString().length>=6 && edt_nickname_signin.text.toString().length<8&& Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())
                    && Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString()) && Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                    btn_signin.isEnabled = true
                    btn_signin.setBackgroundColor(Color.parseColor("#ffc107"))
                    btn_signin.setOnClickListener {
                        val signin_u_id: String = edt_id_siginin.text.toString()
                        val signin_u_name: String = edt_name_siginin.text.toString()
                        val signin_u_nickname: String = edt_nickname_signin.text.toString()
                        val signin_u_pw: String = edt_pw_siginin.text.toString()
                        postSignupResponse(signin_u_id, signin_u_name, signin_u_nickname, signin_u_pw)
                    }
                }
                else {
                    btn_signin.isEnabled = false
                    btn_signin.setBackgroundColor(Color.parseColor("#767676"))
                }
            }
        })

        edt_pwck_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(edt_pw_siginin.text.toString() != edt_pwck_siginin.text.toString()){
                    pwck_error.visibility=View.VISIBLE
                    edt_pwck_siginin.setBackgroundResource(R.drawable.signin_error_border)
                }
                else{
                    pwck_error.visibility=View.INVISIBLE
                    edt_pwck_siginin.setBackgroundResource(R.drawable.signin_enter_border)
                }
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != ""
                    && edt_pw_siginin.text.toString().length>=6 && edt_nickname_signin.text.toString().length<8&& Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())
                    && Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString()) && Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                    btn_signin.isEnabled = true
                    btn_signin.setBackgroundColor(Color.parseColor("#ffc107"))
                    btn_signin.setOnClickListener {
                        val signin_u_id: String = edt_id_siginin.text.toString()
                        val signin_u_name: String = edt_name_siginin.text.toString()
                        val signin_u_nickname: String = edt_nickname_signin.text.toString()
                        val signin_u_pw: String = edt_pw_siginin.text.toString()
                        postSignupResponse(signin_u_id, signin_u_name, signin_u_nickname, signin_u_pw)
                    }
                }
                else {
                    btn_signin.isEnabled = false
                    btn_signin.setBackgroundColor(Color.parseColor("#767676"))
                }
            }
        })

        edt_nickname_signin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(edt_nickname_signin.text.toString().length >= 8 ||!Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())){
                    nick_error.visibility=View.VISIBLE
                    edt_nickname_signin.setBackgroundResource(R.drawable.signin_error_border)
                }
                else{
                    nick_error.visibility=View.INVISIBLE
                    edt_nickname_signin.setBackgroundResource(R.drawable.signin_enter_border)
                }
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != ""
                    && edt_pw_siginin.text.toString().length>=6 && edt_nickname_signin.text.toString().length<8&& Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())
                    && Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString()) && Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                    btn_signin.isEnabled = true
                    btn_signin.setBackgroundColor(Color.parseColor("#ffc107"))
                    btn_signin.setOnClickListener {
                        val signin_u_id: String = edt_id_siginin.text.toString()
                        val signin_u_name: String = edt_name_siginin.text.toString()
                        val signin_u_nickname: String = edt_nickname_signin.text.toString()
                        val signin_u_pw: String = edt_pw_siginin.text.toString()
                        postSignupResponse(signin_u_id, signin_u_name, signin_u_nickname, signin_u_pw)
                    }
                }
                else {
                    btn_signin.isEnabled = false
                    btn_signin.setBackgroundColor(Color.parseColor("#767676"))
                }
            }
        })
    }


    fun postSignupResponse(u_id: String, u_name: String, u_nickname: String, u_pw: String) {
        var jsonObject = JSONObject()
        jsonObject.put("userId", u_id)
        jsonObject.put("name", u_name)
        jsonObject.put("nickname", u_nickname)
        jsonObject.put("password", u_pw)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSignupResponse: Call<PostSignupResponse> =
            networkService.postSignupResponse("application/json", gsonObject)
        postSignupResponse.enqueue(object: Callback<PostSignupResponse> {
            override fun onFailure(call: Call<PostSignupResponse>, t: Throwable) {
                Log.e("tag", "회원가입 실패")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<PostSignupResponse>, response: Response<PostSignupResponse>) {
                if (response.isSuccessful){
                    toast(response.body()!!.message)
                    if (response.body()!!.status == 200){
                        startActivity<LoginActivity>()
                    }
                }
            }
        })
    }

}

