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
    var areaList = arrayListOf<String>()
    var ageList = arrayListOf<String>()
    var categoryList = arrayListOf<String>()
    var requestCode = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val intent = getIntent()
        requestCode = intent.getIntExtra("requestCode", 0)

        if(requestCode == 1100){
            findViewById<TextView>(R.id.txt_title_toolbar_filter).text = "필터"
            btn_finish_category.text = "필터 적용하기"
       }else{
            findViewById<TextView>(R.id.txt_title_toolbar_filter).text = "카테고리 선택"
        }


        categoryAllClick(cb_area_all_category, ll_area_category, 26,"cb_area_category", areaList)
        categoryAllClick(cb_age_all_category, ll_age_category, 5,"cb_age_category", ageList)
        categoryAllClick(cb_category_all_category, ll_category_category, 10,"cb_category_category_", categoryList)
        for(i in 2..26){
            cbClick(ll_area_category,"cb_area_category",i,cb_area_all_category, 26, areaList)
        }

        for(i in 2..5){
            cbClick(ll_age_category, "cb_age_category",i,cb_age_all_category, 5, ageList)
        }

        for(i in 2..10){
            cbClick(ll_category_category, "cb_category_category_", i, cb_category_all_category, 10, categoryList)
        }
    }

    override fun onResume() {
        super.onResume()
        btn_finish_category.setOnClickListener {
            if(requestCode == 1100){
                noCategory()
            }else{
                isValid()
            }
        }
    }

    fun noCategory(){
        if(noCategoryClick(ll_area_category,"cb_area_category",26)){
            areaList.add(cb_area_all_category.text.toString())
        }
        if(noCategoryClick(ll_age_category, "cb_age_category", 5)){
            ageList.add(cb_age_all_category.text.toString())
        }
        if(noCategoryClick(ll_category_category, "cb_category_category_",10)){
            categoryList.add(cb_category_all_category.text.toString())
        }
        val intent  = Intent()
        intent.putExtra("areaList", areaList)
        intent.putExtra("ageList", ageList)
        intent.putExtra("categoryList", categoryList)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    fun isValid(){
        if(noCategoryClick(ll_area_category,"cb_area_category",26)){
            WritePostActivity.showNoticeDialog(this, "자치구를 선택해주세요!\n", "자치구를 선택해야", "글을 작성할 수 있습니다.")
        }else if(noCategoryClick(ll_age_category, "cb_age_category", 5)){
            WritePostActivity.showNoticeDialog(this, "나이를 선택해주세요!\n", "나이를 선택해야", "글을 작성할 수 있습니다.")
        }else if(noCategoryClick(ll_category_category, "cb_category_category_",10)){
            WritePostActivity.showNoticeDialog(this, "카테고리를 선택해주세요!\n", "카테고리를 선택해야", "글을 작성할 수 있습니다.")
        }else{
            val intent  = Intent()
            intent.putExtra("areaList", areaList)
            intent.putExtra("ageList", ageList)
            intent.putExtra("categoryList", categoryList)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }


    }

    //전체 버튼 클릭
    fun categoryAllClick(cb : CheckBox, ll : LinearLayout, cbNum : Int, tag : String, list: ArrayList<String>){
        cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(cb.isChecked){
                for(i in 1..cbNum){
                    ll.findViewWithTag<CheckBox>(tag + "$i").isChecked = true
                    ll.findViewWithTag<CheckBox>(tag + "$i").setTextColor(applicationContext.resources.getColor(R.color.white))
                    list.remove(ll.findViewWithTag<CheckBox>(tag + "$i").text.toString())
                }
                list.add(cb.text.toString())
            }else{
                for(i in 1..cbNum){
                    ll.findViewWithTag<CheckBox>(tag + "$i").isChecked = false
                    ll.findViewWithTag<CheckBox>(tag + "$i").setTextColor(applicationContext.resources.getColor(R.color.grey))
                    list.remove(ll.findViewWithTag<CheckBox>(tag + "$i").text.toString())
                }
            }
        }
    }
    //버튼 클릭
    fun cbClick(ll: LinearLayout,tag : String, tagNum : Int, cb : CheckBox, cbNum: Int,list: ArrayList<String>){
        val id = ll.findViewWithTag<CheckBox>(tag + "$tagNum")
        id.setOnCheckedChangeListener { buttonView, isChecked ->

            //전체버튼 true인데 버튼 눌렀을때(전체 false)
                id.setOnClickListener {
                    if(cb.isChecked) {
                        cb.isChecked = false

                        for (i in 2..cbNum) {
                            ll.findViewWithTag<CheckBox>(tag + "$i").isChecked = true
                            ll.findViewWithTag<CheckBox>(tag + "$i")
                                .setTextColor(applicationContext.resources.getColor(R.color.white))
                            list.add(ll.findViewWithTag<CheckBox>(tag + "$i").text.toString())
                        }

                        id.isChecked = false
                        id.setTextColor(applicationContext.resources.getColor(R.color.grey))
                        list.remove(id.text.toString())

                    }

                    if(allCategoryClickExctBtnAll(ll,tag,cbNum)){
                        cb.isChecked = true
                        cb.setTextColor(applicationContext.resources.getColor(R.color.white))
                        list.add(cb.text.toString())
                        for(i in 2..cbNum){
                            list.remove(ll.findViewWithTag<CheckBox>(tag + "$i").text.toString())
                        }
                    }

                }

            //버튼 클릭
                if(id.isChecked){
                    id.isChecked = true
                    id.setTextColor(applicationContext.resources.getColor(R.color.white))
                    list.add(id.text.toString())

                }else{
                    id.isChecked = false
                    id.setTextColor(applicationContext.resources.getColor(R.color.grey))
                    list.remove(id.text.toString())
                }
        }
    }

    //모든 버튼이 눌렸을 때
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

    //버튼이 하나도 눌리지 않았을 때
    fun noCategoryClick(ll : LinearLayout, tag: String, cbNum: Int) : Boolean{
        var state = false
        loop@ for(i in 2..cbNum) {
            val categoryState = ll.findViewWithTag<CheckBox>(tag +"$i").isChecked
            if(categoryState) break@loop    //하나라도 true가 나오면 루프 break
            if(!ll.findViewWithTag<CheckBox>(tag +"$cbNum").isChecked && i == cbNum){
                state= true
            }
        }

        return state
    }
}
