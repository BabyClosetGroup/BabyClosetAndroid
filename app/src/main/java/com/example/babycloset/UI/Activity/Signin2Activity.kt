package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_signin2.*

class Signin2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin2)
        if(!edt_nickname_signin.text.equals(""))
            btn_signin.isEnabled=true
        else
            btn_signin.isEnabled=false
        btn_signin.setOnClickListener {
            //로그인페이지
        }
    }
}
