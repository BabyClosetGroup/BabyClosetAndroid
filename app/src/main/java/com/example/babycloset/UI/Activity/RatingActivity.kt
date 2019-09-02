package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_rating.*

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        //이름, 상품명 get
        // 5개의 별!
        ratingStar.numStars=5
        // 별점주기 버튼에 post
        btn_rating
        // 액티비티 종료 버튼
        btn_close.setOnClickListener {
            finish()
        }
    }
}
