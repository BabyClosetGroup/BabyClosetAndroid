package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.MainPageAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureMainTab()
    }

    private fun configureMainTab(){
        vp_ac_main_frag_pager.adapter=MainPageAdapter(supportFragmentManager,2)
        vp_ac_main_frag_pager.offscreenPageLimit=1
        tl_ac_main_bottomTab.setupWithViewPager(vp_ac_main_frag_pager)

        val navCategoryMainLayout: View =(this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.bottom_navigation_tab,null,false)

        tl_ac_main_bottomTab.getTabAt(0)!!.customView=navCategoryMainLayout.findViewById(R.id.btn_bottom_navi_home_tab) as RelativeLayout
        tl_ac_main_bottomTab.getTabAt(1)!!.customView=navCategoryMainLayout.findViewById(R.id.btn_bottom_navi_my_page_tab) as RelativeLayout
    }
}
