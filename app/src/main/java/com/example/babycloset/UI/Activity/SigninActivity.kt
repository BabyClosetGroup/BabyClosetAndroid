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
import java.util.regex.Pattern

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

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
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != "") {
                    btn_signin.isEnabled = true
                    if(Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())){
                        if(Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString())){
                            if(Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                                btn_signin.setOnClickListener {
                                    /*
                                val signup_u_name: String = edt_signup_name.text.toString()
                                val signup_u_id: String = edt_signup_id.text.toString()
                                val signup_u_pw: String = edt_signup_password.text.toString()
                                postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                                    startActivity<MainActivity>()
                                }
                            }else{
                                btn_signin.setOnClickListener {
                                    toast("닉네임 형식 오류").show()}
                            }
                        } else {
                            btn_signin.setOnClickListener {
                            toast("아이디 형식 오류").show()}
                        }
                    } else {
                        btn_signin.setOnClickListener {
                            toast("비밀번호 형식 오류").show()
                        }
                    }
                }
                else {
                    btn_signin.isEnabled = false
                }
            }
        })

        edt_id_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != "") {
                    btn_signin.isEnabled = true
                    if(Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())){
                        if(Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString())){
                            if(Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                                btn_signin.setOnClickListener {
                                    /*
                                val signup_u_name: String = edt_signup_name.text.toString()
                                val signup_u_id: String = edt_signup_id.text.toString()
                                val signup_u_pw: String = edt_signup_password.text.toString()
                                postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                                    startActivity<MainActivity>()
                                }
                            }else{
                                btn_signin.setOnClickListener {
                                    toast("닉네임 형식 오류").show()}
                            }
                        } else {
                            btn_signin.setOnClickListener {
                                toast("아이디 형식 오류").show()}
                        }
                    } else {
                        btn_signin.setOnClickListener {
                            toast("비밀번호 형식 오류").show()
                        }
                    }
                }
                else {
                    btn_signin.isEnabled = false
                }
            }
        })

        edt_pw_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != "") {
                    btn_signin.isEnabled = true
                    if(Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())){
                        if(Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString())){
                            if(Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                                btn_signin.setOnClickListener {
                                    /*
                                val signup_u_name: String = edt_signup_name.text.toString()
                                val signup_u_id: String = edt_signup_id.text.toString()
                                val signup_u_pw: String = edt_signup_password.text.toString()
                                postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                                    startActivity<MainActivity>()
                                }
                            }else{
                                btn_signin.setOnClickListener {
                                    toast("닉네임 형식 오류").show()}
                            }
                        } else {
                            btn_signin.setOnClickListener {
                                toast("아이디 형식 오류").show()}
                        }
                    } else {
                        btn_signin.setOnClickListener {
                            toast("비밀번호 형식 오류").show()
                        }
                    }
                }
                else {
                    btn_signin.isEnabled = false
                }
            }
        })

        edt_pwck_siginin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != "") {
                    btn_signin.isEnabled = true
                    if(Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())){
                        if(Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString())){
                            if(Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                                btn_signin.setOnClickListener {
                                    /*
                                val signup_u_name: String = edt_signup_name.text.toString()
                                val signup_u_id: String = edt_signup_id.text.toString()
                                val signup_u_pw: String = edt_signup_password.text.toString()
                                postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                                    startActivity<MainActivity>()
                                }
                            }else{
                                btn_signin.setOnClickListener {
                                    toast("닉네임 형식 오류").show()}
                            }
                        } else {
                            btn_signin.setOnClickListener {
                                toast("아이디 형식 오류").show()}
                        }
                    } else {
                        btn_signin.setOnClickListener {
                            toast("비밀번호 형식 오류").show()
                        }
                    }
                }
                else {
                    btn_signin.isEnabled = false
                }
            }
        })

        edt_nickname_signin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_pw_siginin.text.toString() == edt_pwck_siginin.text.toString() && edt_id_siginin.text.toString() != "" && edt_name_siginin.text.toString() != ""
                    && edt_pw_siginin.text.toString() != "" && edt_pwck_siginin.text.toString() != ""&& edt_nickname_signin.text.toString() != "") {
                    btn_signin.isEnabled = true
                    if(Pattern.matches("^[a-zA-Z0-9]*$",edt_pw_siginin.text.toString())){
                        if(Pattern.matches("^[a-zA-Z0-9]*$",edt_id_siginin.text.toString())){
                            if(Pattern.matches("^[가-힣]*$",edt_nickname_signin.text.toString())) {
                                btn_signin.setOnClickListener {
                                    /*
                                val signup_u_name: String = edt_signup_name.text.toString()
                                val signup_u_id: String = edt_signup_id.text.toString()
                                val signup_u_pw: String = edt_signup_password.text.toString()
                                postSignupResponse(signup_u_name, signup_u_id, signup_u_pw)*/
                                    startActivity<MainActivity>()
                                }
                            }else{
                                btn_signin.setOnClickListener {
                                    toast("닉네임 형식 오류").show()}
                            }
                        } else {
                            btn_signin.setOnClickListener {
                                toast("아이디 형식 오류").show()}
                        }
                    } else {
                        btn_signin.setOnClickListener {
                            toast("비밀번호 형식 오류").show()
                        }
                    }
                }
                else {
                    btn_signin.isEnabled = false
                }
            }
        })

    }
}

