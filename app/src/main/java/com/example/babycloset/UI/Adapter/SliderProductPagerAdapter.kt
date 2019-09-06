package com.example.babycloset.UI.Adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.babycloset.UI.Fragment.SliderProductFragment

class SliderProductPagerAdapter (fm : FragmentManager?, val num_fragment : Int) : FragmentStatePagerAdapter(fm){
    override fun getItem(p0: Int): Fragment {
        var fragment : SliderProductFragment = SliderProductFragment()
        var bundle = Bundle(1)
        when(p0){
            0->bundle.putString("url","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg")
            1->bundle.putString("url","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg")
            2->bundle.putString("url","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg")
            3->bundle.putString("url","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg")
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return num_fragment
    }
}