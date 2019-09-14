package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.LinearLayout
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

        categoryAllClick(cb_area_all_category, ll_area_category, 26,"cb_area_category")
        categoryAllClick(cb_age_all_category, ll_age_category, 5,"cb_age_category")
        categoryAllClick(cb_category_all_category, ll_category_category, 10,"cb_category_category_")
        for(i in 2..26){
            cbClick(ll_area_category,"cb_area_category",i,cb_area_all_category, 26)
        }

        for(i in 2..5){
            cbClick(ll_age_category, "cb_age_category",i,cb_age_all_category, 5 )
        }

        for(i in 2..10){
            cbClick(ll_category_category, "cb_category_category_", i, cb_category_all_category, 10)
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

    fun categoryAllClick(cb : CheckBox, ll : LinearLayout, cbNum : Int, tag : String){
        cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(cb.isChecked){
                for(i in 1..cbNum){
                    ll.findViewWithTag<CheckBox>(tag + "$i").isChecked = true
                    ll.findViewWithTag<CheckBox>(tag + "$i").setTextColor(applicationContext.resources.getColor(R.color.white))
                }
            }else{
                for(i in 1..cbNum){
                    ll.findViewWithTag<CheckBox>(tag + "$i").isChecked = false
                    ll.findViewWithTag<CheckBox>(tag + "$i").setTextColor(applicationContext.resources.getColor(R.color.grey))
                }
            }
        }
    }
    //자치구
    fun cbClick(ll: LinearLayout,tag : String, tagNum : Int, cb : CheckBox, cbNum: Int){
        val id = ll.findViewWithTag<CheckBox>(tag + "$tagNum")
        id.setOnCheckedChangeListener { buttonView, isChecked ->

                id.setOnClickListener {
                    if(cb.isChecked) {
                        cb.isChecked = false

                        for (i in 2..cbNum) {
                            ll.findViewWithTag<CheckBox>(tag + "$i").isChecked = true
                            ll.findViewWithTag<CheckBox>(tag + "$i")
                                .setTextColor(applicationContext.resources.getColor(R.color.white))
                        }

                        id.isChecked = false
                        id.setTextColor(applicationContext.resources.getColor(R.color.grey))
                    }

                    if(allCategoryClickExctBtnAll(ll,tag,cbNum)){
                        cb.isChecked = true
                        cb.setTextColor(applicationContext.resources.getColor(R.color.white))
                    }

                }

                if(id.isChecked){
                    id.isChecked = true
                    id.setTextColor(applicationContext.resources.getColor(R.color.white))

                }else{
                    id.isChecked = false
                    id.setTextColor(applicationContext.resources.getColor(R.color.grey))
                }
        }
    }

    fun allCategoryClickExctBtnAll(ll : LinearLayout, tag: String, cbNum: Int) : Boolean{

        var state = false
            loop@ for(i in 2..cbNum) {
                val categoryState = ll.findViewWithTag<CheckBox>(tag +"$i").isChecked
                if(!categoryState) break@loop
                if(ll.findViewWithTag<CheckBox>(tag +"$cbNum").isChecked && i == cbNum){
                    state= true
                }
            }

        return state
    }

}
