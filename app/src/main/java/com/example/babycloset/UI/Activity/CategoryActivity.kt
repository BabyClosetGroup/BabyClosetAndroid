package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_filter.*

class CategoryActivity : AppCompatActivity() {

   var areaCategoryIsChecked : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        findViewById<TextView>(R.id.txt_title_toolbar_filter).text = "카테고리 선택"

        //configCheckbox()

        for(i in 1..26){
            ll_area_category.findViewWithTag<CheckBox>("cb_area_category$i").setOnClickListener {
                areaBtnClick(i)
            }
        }

    }

    fun areaBtnClick(tag : Int){
        val id = ll_area_category.findViewWithTag<CheckBox>("cb_area_category$tag")
        id.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                id.setTextColor(applicationContext.resources.getColor(R.color.white))
                for(i in 1..26){
                    ll_area_category.findViewWithTag<CheckBox>("cb_area_category$i").isEnabled = false
                    if(ll_area_category.findViewWithTag<CheckBox>("cb_area_category$i") == id){
                        id.isEnabled = true
                    }
                    areaCategoryIsChecked = true
                }
            }else{
                for(i in 1..26){
                    ll_area_category.findViewWithTag<CheckBox>("cb_area_category$i").isEnabled = true
                }
                id.setTextColor(applicationContext.resources.getColor(R.color.grey))
                areaCategoryIsChecked = false

            }
        }


    }

}
