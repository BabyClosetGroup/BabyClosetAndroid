package com.example.babycloset.UI.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babycloset.R
import android.os.Build
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.ShapeDrawable
import com.example.babycloset.UI.Activity.EditInfoActivity
import com.example.babycloset.UI.Activity.ReceiveProductActivity
import com.example.babycloset.UI.Activity.ShareProductActivity
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.toolbar_mypage.*
import org.jetbrains.anko.support.v4.startActivity


class MyPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 이름, 평균 별점 가져오기

        // 원형이미지

        // 나눈 상품
        btn_to_share.setOnClickListener {
            startActivity<ShareProductActivity>()
        }

        // 받은 상품
        btn_to_receive.setOnClickListener {
            startActivity<ReceiveProductActivity>()
        }
        configureTitleBar()
    }
    private fun configureTitleBar() {
        btn_edit_info.setOnClickListener {
            startActivity<EditInfoActivity>()
        }
    }
}
