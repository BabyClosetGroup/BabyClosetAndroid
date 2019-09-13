package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_filter.*
import org.jetbrains.anko.toast

class CategoryActivity : AppCompatActivity() {
    var area : String = ""
    var age : String = ""
    var category : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        findViewById<TextView>(R.id.txt_title_toolbar_filter).text = "카테고리 선택"

        //configCheckbox()

        for(i in 1..26){
            areaBtnClick(i)
        }

        for(i in 1..5){
            ageBtnClick(i)
        }

        for(i in 1..10){
            categoryBtnClick(i)
        }


    }

    override fun onResume() {
        super.onResume()
        btn_finish_category.setOnClickListener {
            isValid()

        }
    }

    fun isValid(){
        if(area == ""){
            WritePostActivity.showNoticeDialog(this, "자치구를 선택해주세요!\n", "자치구를 선택해야", "글을 작성할 수 있습니다.")
        }else if(age == ""){
            WritePostActivity.showNoticeDialog(this, "나이를 선택해주세요!\n", "나이를 선택해야", "글을 작성할 수 있습니다.")
        }else if(category == ""){
            WritePostActivity.showNoticeDialog(this, "카테고리를 선택해주세요!\n", "카테고리를 선택해야", "글을 작성할 수 있습니다.")
        }else{
            val intent  = Intent()
            intent.putExtra("area", area)
            intent.putExtra("age", age)
            intent.putExtra("category", category)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    //자치구
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
                    area = id.text.toString()
                }
            }else{
                for(i in 1..26){
                    ll_area_category.findViewWithTag<CheckBox>("cb_area_category$i").isEnabled = true
                }
                id.setTextColor(applicationContext.resources.getColor(R.color.grey))
            }
        }
    }

    //나이
    fun ageBtnClick(tag:Int){
        val id = ll_age_category.findViewWithTag<CheckBox>("cb_age_category$tag")
        id.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                id.setTextColor(applicationContext.resources.getColor(R.color.white))
                for(i in 1..5){
                    ll_age_category.findViewWithTag<CheckBox>("cb_age_category$i").isEnabled = false
                    if(ll_age_category.findViewWithTag<CheckBox>("cb_age_category$i") == id){
                        id.isEnabled = true
                    }
                    age = id.text.toString()
                }
            }else{
                for(i in 1..5){
                    ll_age_category.findViewWithTag<CheckBox>("cb_age_category$i").isEnabled = true
                }
                id.setTextColor(applicationContext.resources.getColor(R.color.grey))
            }
        }

    }
    //카테고리
    fun categoryBtnClick(tag: Int){
        val id = ll_category_category.findViewWithTag<CheckBox>("cb_category_category_$tag")
        id.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                id.setTextColor(applicationContext.resources.getColor(R.color.white))
                for(i in 1..10){
                    ll_category_category.findViewWithTag<CheckBox>("cb_category_category_$i").isEnabled = false
                    if(ll_category_category.findViewWithTag<CheckBox>("cb_category_category_$i") == id){
                        id.isEnabled = true
                    }
                    category = id.text.toString()
                }
            }else{
                for(i in 1..10){
                    ll_category_category.findViewWithTag<CheckBox>("cb_category_category_$i").isEnabled = true
                }
                id.setTextColor(applicationContext.resources.getColor(R.color.grey))
            }
        }
    }

}