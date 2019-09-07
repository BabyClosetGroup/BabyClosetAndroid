package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //아이디 한글 막기
        //비밀번호 6자이상, 한글막기
        //비밀번호 확인
        //버튼으로 아이디, 비번, 이름 전송
        btn_next_signin.isEnabled=false

        edt_name_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != "") {
                    btn_next_signin.isEnabled = true
                    btn_next_signin.setOnClickListener {
                        /*
                        val signup_u_name: String = edt_signup_name.text.toString()
                        val signup_u_id: String = edt_signup_id.text.toString()
                        val signup_u_pw: String = edt_signup_password.text.toString()
                        postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                        startActivity<Signin2Activity>()
                    }
                }
                else {
                    btn_next_signin.isEnabled = false
                }
            }
        })

        edt_id_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != "") {
                    btn_next_signin.isEnabled = true
                    btn_next_signin.setOnClickListener {
                        /*val signup_u_name: String = edt_name_siginin.text.toString()
                        val signup_u_id: String = edt_id_siginin.text.toString()
                        val signup_u_pw: String = edt_pw_siginin.text.toString()

                        postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                        startActivity<Signin2Activity>()
                    }
                }
                else {
                    btn_next_signin.isEnabled = false
                }
            }
        })

        edt_pw_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != "") {
                    btn_next_signin.isEnabled = true
                    btn_next_signin.setOnClickListener {
                        /*val signup_u_name: String = edt_name_siginin.text.toString()
                        val signup_u_id: String = edt_id_siginin.text.toString()
                        val signup_u_pw: String = edt_pw_siginin.text.toString()

                        postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                        startActivity<Signin2Activity>()
                    }
                }
                else {
                    btn_next_signin.isEnabled = false
                }
            }
        })

        edt_pwck_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != "") {
                    btn_next_signin.isEnabled = true
                    btn_next_signin.setOnClickListener {
                        /*val signup_u_name: String = edt_name_siginin.text.toString()
                        val signup_u_id: String = edt_id_siginin.text.toString()
                        val signup_u_pw: String = edt_pw_siginin.text.toString()

                        postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                        startActivity<Signin2Activity>()
                    }
                }
                else {
                    btn_next_signin.isEnabled = false
                }
            }
        })
    }
}

