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

        btn_agree_info.isEnabled=false

        btn_agree_all_info.setOnClickListener {
            if(btn_agree_all_select.isSelected==false) {
                btn_agree_all_select.isSelected = true
                btn_agree_info.isEnabled=true
                info_consent_border.setImageResource(R.drawable.yellow_fill_border)
            } else {
                btn_agree_all_select.isSelected=false
                btn_agree_info.isEnabled=false
                info_consent_border.setImageResource(R.drawable.gray_fill_border)
            }
        }

        btn_agree1_info.setOnClickListener {
            startActivity<info1Activity>()
        }
        btn_agree2_info.setOnClickListener {
            startActivity<info2Activity>()
        }

        // 전체동의시에만 처리되게 해야함
        btn_agree_info.setOnClickListener {
            if(btn_agree_all_select.isSelected==true)
                startActivity<SignupActivity>()
        }
    }
}
