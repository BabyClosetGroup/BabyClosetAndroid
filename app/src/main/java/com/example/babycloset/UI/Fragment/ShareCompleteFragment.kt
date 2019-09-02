package com.example.babycloset.UI.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.R

class ShareCompleteFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_complete, container, false)

        //상품정보 가져오기
        //별점주기 누르면 창이동, 별점 부여시 버튼 사라지게
        //정보보기 누르면 상대 평점점
   }
}
