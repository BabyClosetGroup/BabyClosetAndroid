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
            val login_u_id: String = edt_id_login.text.toString()
            val login_u_pw: String = edt_pw_login.text.toString()

            //postLoginResponse(login_u_id, login_u_pw)
            startActivity<MainActivity>()
        }
    }
}
