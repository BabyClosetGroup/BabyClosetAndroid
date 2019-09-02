package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R

class ReceiveProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_product)

        //상품정보 불러오기
        //별점이 있으면 버튼 없어지게
        //별점주기 누르면 별점액티비티이동
        //정보보기 누르면 상대방의 평점팝업
    }
}
