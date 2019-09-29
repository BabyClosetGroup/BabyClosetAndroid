package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
    }
//        configCheckbox()

//        cb_area_all.setOnClickListener {
//
//            if(cb_area_all.isChecked==false)
//            {
//                cb_area_gangnam.isChecked=false
//                cb_area_gangdong.isChecked=false
//                cb_area_gangbuk.isChecked=false
//                cb_area_gangseo.isChecked=false
//                cb_area_gwanak.isChecked=false
//                cb_area_gwangjin.isChecked=false
//                cb_area_guro.isChecked=false
//                cb_area_geumcheon.isChecked=false
//                cb_area_nowon.isChecked=false
//                cb_area_dobong.isChecked=false
//                cb_area_dongdaemon.isChecked=false
//                cb_area_dongjag.isChecked=false
//                cb_area_mapo.isChecked=false
//                cb_area_seodaemon.isChecked=false
//                cb_area_seocho.isChecked=false
//                cb_area_seongdong.isChecked=false
//                cb_area_seongbuk.isChecked=false
//                cb_area_songpa.isChecked=false
//                cb_area_yangcheon.isChecked=false
//                cb_area_yeongdeungpo.isChecked=false
//                cb_area_yongsan.isChecked=false
//                cb_area_eunpyeong.isChecked=false
//                cb_area_jongro.isChecked=false
//                cb_area_jung.isChecked=false
//                cb_area_junglang.isChecked=false
//
//            }else{
//                cb_area_gangnam.isChecked=true
//                cb_area_gangdong.isChecked=true
//                cb_area_gangbuk.isChecked=true
//                cb_area_gangseo.isChecked=true
//                cb_area_gwanak.isChecked=true
//                cb_area_gwangjin.isChecked=true
//                cb_area_guro.isChecked=true
//                cb_area_geumcheon.isChecked=true
//                cb_area_nowon.isChecked=true
//                cb_area_dobong.isChecked=true
//                cb_area_dongdaemon.isChecked=true
//                cb_area_dongjag.isChecked=true
//                cb_area_mapo.isChecked=true
//                cb_area_seodaemon.isChecked=true
//                cb_area_seocho.isChecked=true
//                cb_area_seongdong.isChecked=true
//                cb_area_seongbuk.isChecked=true
//                cb_area_songpa.isChecked=true
//                cb_area_yangcheon.isChecked=true
//                cb_area_yeongdeungpo.isChecked=true
//                cb_area_yongsan.isChecked=true
//                cb_area_eunpyeong.isChecked=true
//                cb_area_jongro.isChecked=true
//                cb_area_jung.isChecked=true
//                cb_area_junglang.isChecked=true
//            }
//        }
//
//        cb_age_all.setOnClickListener {
//            if (cb_age_all.isChecked == false) {
//                cb_age_3.isChecked = false
//                cb_age_12.isChecked = false
//                cb_age_24.isChecked = false
//                cb_age_30.isChecked = false
//            } else {
//                cb_age_3.isChecked = true
//                cb_age_12.isChecked = true
//                cb_age_24.isChecked = true
//                cb_age_30.isChecked = true
//            }
//        }
//
//        cb_category_all.setOnClickListener {
//            if(cb_category_all.isChecked==false){
//                cb_category_vest.isChecked=false
//                cb_category_inner.isChecked=false
//                cb_category_bodysuit.isChecked=false
//                cb_category_under.isChecked=false
//                cb_category_seulliping.isChecked=false
//                cb_category_dress.isChecked=false
//                cb_category_top.isChecked=false
//                cb_category_bottom.isChecked=false
//                cb_category_topbottom.isChecked=false
//            }else{
//                cb_category_vest.isChecked=true
//                cb_category_inner.isChecked=true
//                cb_category_bodysuit.isChecked=true
//                cb_category_under.isChecked=true
//                cb_category_seulliping.isChecked=true
//                cb_category_dress.isChecked=true
//                cb_category_top.isChecked=true
//                cb_category_bottom.isChecked=true
//                cb_category_topbottom.isChecked=true
//            }
//        }
//
//    }
//
//    private fun configCheckbox(){
//        //서울 전체
//        cb_area_all.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_all.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_all.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //강남구
//        cb_area_gangnam.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_gangnam.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_gangnam.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //강동구
//        cb_area_gangdong.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_gangdong.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_gangdong.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //강북구
//        cb_area_gangbuk.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_gangbuk.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_gangbuk.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //강서구
//        cb_area_gangseo.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_gangseo.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_gangseo.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //관악구
//        cb_area_gwanak.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_gwanak.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_gwanak.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //광진구
//        cb_area_gwangjin.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_gwangjin.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_gwangjin.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //구로구
//        cb_area_guro.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_guro.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_guro.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //금천구
//        cb_area_geumcheon.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_geumcheon.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_geumcheon.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //노원구
//        cb_area_nowon.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_nowon.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_nowon.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //도봉구
//        cb_area_dobong.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_dobong.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_dobong.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //동대문구
//        cb_area_dongdaemon.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_dongdaemon.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_dongdaemon.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //동작구
//        cb_area_dongjag.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_dongjag.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_dongjag.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //마포구
//        cb_area_mapo.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_mapo.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_mapo.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //서대문구
//        cb_area_seodaemon.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_seodaemon.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_seodaemon.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //서초구
//        cb_area_seocho.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_seocho.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_seocho.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //성동구
//        cb_area_seongdong.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_seongdong.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_seongdong.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //성북구
//        cb_area_seongbuk.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_seongbuk.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_seongbuk.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //송파구
//        cb_area_songpa.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_songpa.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_songpa.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //양천구
//        cb_area_yangcheon.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_yangcheon.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_yangcheon.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //영등포구
//        cb_area_yeongdeungpo.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_yeongdeungpo.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_yeongdeungpo.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //용산구
//        cb_area_yongsan.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_yongsan.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_yongsan.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //은평구
//        cb_area_eunpyeong.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_eunpyeong.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_eunpyeong.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //종로구
//        cb_area_jongro.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_jongro.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_jongro.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //중구
//        cb_area_jung.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_jung.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_jung.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //중랑구
//        cb_area_junglang.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_area_junglang.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_area_junglang.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //나이_all
//        cb_age_all.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_age_all.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_age_all.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//        //나이_3
//        cb_age_3.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_age_3.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_age_3.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//        //나이_12
//        cb_age_12.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_age_12.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_age_12.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//        //나이_24
//        cb_age_24.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_age_24.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_age_24.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//        //나이_30
//        cb_age_30.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_age_30.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_age_30.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //카테고리
//        //전체
//        cb_category_all.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_all.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_all.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //베스트
//        cb_category_vest.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_vest.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_vest.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //배내옷
//        cb_category_inner.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_inner.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_inner.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //바디슈트
//        cb_category_bodysuit.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_bodysuit.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_bodysuit.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //내의
//        cb_category_under.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_under.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_under.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //슬리핑가운
//        cb_category_seulliping.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_seulliping.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_seulliping.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //원피스
//        cb_category_dress.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_dress.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_dress.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//
//        //상의
//        cb_category_top.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_top.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_top.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//        //하의
//        cb_category_bottom.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_bottom.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_bottom.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//        //상하복
//        cb_category_topbottom.setOnCheckedChangeListener { button, isChecked ->
//            if(isChecked){
//                cb_category_topbottom.setTextColor(applicationContext.resources.getColor(R.color.white))
//            }else{
//                cb_category_topbottom.setTextColor(applicationContext.resources.getColor(R.color.grey))
//            }
//        }
//    }

}
