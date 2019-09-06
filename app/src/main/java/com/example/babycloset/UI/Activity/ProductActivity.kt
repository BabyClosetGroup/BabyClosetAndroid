package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.SliderProductPagerAdapter
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        imageSlider()
    }

    fun imageSlider(){
        vp_product_slider.adapter = SliderProductPagerAdapter(supportFragmentManager, 4)
        vp_product_slider.offscreenPageLimit = 3
        tl_product_indicator.setupWithViewPager(vp_product_slider)
    }
}
