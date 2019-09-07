package com.example.babycloset.UI.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.babycloset.UI.Activity.WritePostActivity
import com.example.babycloset.UI.Fragment.HomeFragment
import com.example.babycloset.UI.Fragment.MyPageFragment
import com.example.babycloset.UI.Fragment.WriteFragment

class MainPageAdapter (fm: FragmentManager,private val num_fragment: Int):FragmentStatePagerAdapter(fm){
    override fun getItem(p0: Int): Fragment? {
        return when(p0){
            0->HomeFragment()
            1->MyPageFragment()
            else->null
        }
    }

    override fun getCount(): Int {
        return num_fragment
    }
}