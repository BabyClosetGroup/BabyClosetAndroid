package com.example.babycloset.UI.Adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import com.example.babycloset.UI.Activity.ProductActivity
import com.example.babycloset.UI.Fragment.SliderProductFragment

class SliderProductPagerAdapter (fm : FragmentManager?, val num_fragment : Int) : FragmentStatePagerAdapter(fm){

    var imgList = ArrayList<String>()
    override fun getItem(p0: Int): Fragment {
        val fragment : SliderProductFragment = SliderProductFragment()
        val bundle = Bundle(1)

        when(p0){
            //액티비티에서 데이터 받기
            //fragment로 데이터 전달
            0->{
                bundle.putString("url", imgList[0])
            }
            1->{
                bundle.putString("url", imgList[1])
            }
            2->{
                bundle.putString("url", imgList[2])
            }
            3->{
                bundle.putString("url", imgList[3])
            }
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return num_fragment
    }

    fun setUrl(list : ArrayList<String>){
        this.imgList = list
    }
}