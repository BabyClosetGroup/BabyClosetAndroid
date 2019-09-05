package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_info_consent.*
import org.jetbrains.anko.startActivity

class InfoConsentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_consent)

        btn_agree_all_info.setOnClickListener {
            if(btn_agree_all_select.isSelected==false){
                btn_agree_all_select.isSelected=true
                btn_agree1_select.isSelected=true
                btn_agree2_select.isSelected=true
            }else{
                btn_agree_all_select.isSelected=false
                btn_agree1_select.isSelected=false
                btn_agree2_select.isSelected=false
            }
        }
        btn_agree1_info.setOnClickListener {
            if(btn_agree1_select.isSelected==false){
                btn_agree1_select.isSelected=true
                if(btn_agree2_select.isSelected==true)
                    btn_agree_all_select.isSelected=true
            }else{
                btn_agree1_select.isSelected=false
                btn_agree_all_select.isSelected=false
            }
        }
        btn_agree2_info.setOnClickListener {
            if(btn_agree2_select.isSelected==false){
                btn_agree2_select.isSelected=true
                if(btn_agree1_select.isSelected==true)
                    btn_agree_all_select.isSelected=true
            }else{
                btn_agree2_select.isSelected=false
                btn_agree_all_select.isSelected=false
            }
        }
        //둘 다 선택될 때 맨 위 바뀌게

        // 전체동의시에만 처리되게 해야함
        btn_agree_info.setOnClickListener {
            // 회원가입 정보입력 페이지
            startActivity<SigninActivity>()
        }
    }
}
