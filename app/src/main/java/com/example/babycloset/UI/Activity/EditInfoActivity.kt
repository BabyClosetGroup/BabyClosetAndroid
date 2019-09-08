package com.example.babycloset.UI.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_edit_info.*
import kotlinx.android.synthetic.main.toolbar_edit_info.*

class EditInfoActivity : AppCompatActivity() {

    val REQUEST_CODE_SELECT_IMAGE: Int = 1004

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        btn_save_info.setOnClickListener {
            // 데이터 저장
            finish()
        }

        btn_pw_del.setOnClickListener {
            // x클릭시 비번칸 지우기
            txt_info_pw.text=null
        }

        img_info_thumbnail.setOnClickListener {
            //갤러리 연동
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
        }
    }
}
