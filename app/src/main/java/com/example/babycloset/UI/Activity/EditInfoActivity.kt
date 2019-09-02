package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R

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

        //비번은 비활성화되어있다가 변경버튼 눌렀을때 활성화되게
    }
}
