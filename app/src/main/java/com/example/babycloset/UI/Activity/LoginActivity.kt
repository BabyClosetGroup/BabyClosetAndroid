package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //회원가입
        btn_to_signin.setOnClickListener {
            startActivity<InfoConsentActivity>()
        }
        //로그인
        btn_login.setOnClickListener {
            startActivity<MainActivity>()
        }
    }
}
