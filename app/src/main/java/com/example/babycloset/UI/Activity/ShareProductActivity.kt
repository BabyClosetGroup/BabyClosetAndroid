package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.SharePagerAdapter
import kotlinx.android.synthetic.main.activity_share_product.*

class ShareProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_product)

        configureMainTab()
    }
    private fun configureMainTab() {
        vp_share_product.adapter = SharePagerAdapter(supportFragmentManager, 2)
        vp_share_product.offscreenPageLimit = 2
        tl_share_category.setupWithViewPager(vp_share_product)

        val navCategoryMainLayout: View = (this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.navigation_category_share_product, null, false)

        tl_share_category.getTabAt(0)!!.customView = navCategoryMainLayout.findViewById(R.id.rl_nav_category_share_incomplete) as RelativeLayout
        tl_share_category.getTabAt(1)!!.customView = navCategoryMainLayout.findViewById(R.id.rl_nav_category_share_complete) as RelativeLayout
    }

}
