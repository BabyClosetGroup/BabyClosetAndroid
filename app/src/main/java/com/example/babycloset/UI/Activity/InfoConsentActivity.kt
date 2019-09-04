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
    }
}
