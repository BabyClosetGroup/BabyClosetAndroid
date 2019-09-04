package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_info_consent.*

class InfoConsentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_consent)

        btn_agree_all_info.setOnClickListener {
            btn_agree_all_select.isSelected=!btn_agree_all_select.isSelected
            btn_agree1_select.isSelected=!btn_agree1_select.isSelected
            btn_agree2_select.isSelected=!btn_agree2_select.isSelected
        }
        btn_agree1_info.setOnClickListener {
            btn_agree1_select.isSelected=!btn_agree1_select.isSelected
        }
        btn_agree2_info.setOnClickListener {
            btn_agree2_select.isSelected=!btn_agree2_select.isSelected
        }

        if(btn_agree1_select.isSelected==true&&btn_agree2_select.isSelected==true){
            if(btn_agree_all_select.isSelected==false)
                btn_agree_all_select.isSelected=!btn_agree_all_select.isSelected
        }else {
            if (btn_agree_all_select.isSelected == true)
                btn_agree_all_select.isSelected=!btn_agree_all_select.isSelected
        }
        //둘 다 선택될 때 맨 위 바뀌게

        btn_agree_info.setOnClickListener {
            // 회원가입 정보입력 페이지
        }
    }
}
