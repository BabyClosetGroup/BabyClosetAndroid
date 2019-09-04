package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_edit_info.*

class EditInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        //프로필사진 원형 출력
        //통신으로 닉네임, 이름, 아이디, 비밀번호 가져오기?
        //비밀변호변경 어떤식으로..??
        //아이디 이름은 출력만?
        //닉네임 수정기능
        //사진 수정기능(갤러리연동)
        //체크버튼 누를시 저장되고 마이페이지로 이동

        img_info_thumbnail.setOnClickListener {
            //갤러리 연동
        }
    }
}
