package com.example.babycloset.UI.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.babycloset.UI.Fragment.ShareCompleteFragment
import com.example.babycloset.UI.Fragment.ShareIncompleteFragment

class SharePagerAdapter(fm: FragmentManager, private val num_Fragment: Int): FragmentStatePagerAdapter(fm)
{
    override fun getItem(p0: Int): Fragment? {
        return when (p0) {
            0 -> ShareIncompleteFragment()
            1 -> ShareCompleteFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return num_Fragment
    }
}