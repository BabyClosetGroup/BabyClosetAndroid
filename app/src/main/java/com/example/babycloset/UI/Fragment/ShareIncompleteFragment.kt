package com.example.babycloset.UI.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.R

class ShareIncompleteFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_incomplete, container, false)


        //상품정보 가져오기
        //나눔하기 누르면 신청자목록으로
    }
}
