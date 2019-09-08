package com.example.babycloset.UI.Activity

import android.content.DialogInterface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.SliderProductPagerAdapter
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.dialog_custom_complain.view.*
import kotlinx.android.synthetic.main.toolbar_product.*
import org.jetbrains.anko.colorAttr
import org.jetbrains.anko.toast


class ProductActivity : AppCompatActivity() {

    var isSender : Int = 0  //판매자 구매자 구별 변수
    var complainReson : String = "" //신고사유
    lateinit var builderNew: AlertDialog
    lateinit var dialogView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        imageSlider()
        network()

        btn_apply_product.setOnClickListener {
            //어디로 가더라
        }

        btn_ddd_toolbar_product.setOnClickListener {
            if(isSender == 1){  //판매자가 누르면 - 수정하기, 삭제하기 (isSender == 1)
                showSellerDialog()
            }
            if(isSender == 0){  // 구매자가 누르면 - 쪽지보내기, 신고하기 (isSender == 0)
                showBuyerDialog()
            }
        }
    }

    fun imageSlider(){
        vp_product_slider.adapter = SliderProductPagerAdapter(supportFragmentManager, 4)
        vp_product_slider.offscreenPageLimit = 3
        tl_product_indicator.setupWithViewPager(vp_product_slider)
    }

    fun showSellerDialog(){
        val sellerItemList = arrayOf<CharSequence>("수정하기", "삭제하기")
        val builder = AlertDialog.Builder(this)
        builder.setItems(sellerItemList, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0->{
                    //수정하기
                }
                1->{
                    //삭제하기
                }
            }
        })
        builder.show()

    }

    fun showBuyerDialog(){
        val buyerItemList = arrayOf<CharSequence>("쪽지보내기", "신고하기")
        val builder = AlertDialog.Builder(this)
        builder.setItems(buyerItemList, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0->{
                    //쪽지보내기
                }
                1->{
                    //신고하기
                    showComplainDialog()
                }
            }
        })
        builder.show()
    }

    //신고하기 다이얼로그
    fun showComplainDialog(){
       val complainItemList = arrayOf<CharSequence>("잠수", "불량 물건", "기타 (직접 입력)")
        val builder = AlertDialog.Builder(this)
        builder.setItems(complainItemList, DialogInterface.OnClickListener { dialog, which ->
            when(which) {
                0 -> {
                    complainReson = complainItemList[0].toString()
                    toast(complainReson)
                }
                1 -> {
                    complainReson = complainItemList[1].toString()
                    toast(complainReson)
                }
                2 -> {
                    showCustomComplainDialog()
                }
            }
            })
        builder.show()
    }

    //신고하기->직접작성
    fun showCustomComplainDialog(){
        val builder = AlertDialog.Builder(this)
        dialogView = layoutInflater.inflate(R.layout.dialog_custom_complain, null)
        builder.setView(dialogView)
        edtCharCount()
        builderNew = builder.show()
        builderNew.show()

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(builderNew.window.attributes)
        lp.height = 580
        val window = builderNew.window
        window.attributes = lp

    }

    //글자 수 세기
    fun edtCharCount(){
        var str : String = ""
        val id = dialogView.findViewById<EditText>(R.id.edt_dialog_content_complain)
        id.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                str = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length > 50){
                    id.setText(str)
                    id.setSelection(start)
                }else{
                    dialogView.findViewById<TextView>(R.id.txt_edt_count_complain).text = s.length.toString()
                }
            }
        })
    }

    //신고하기(취소/확인)
    fun dialogComplainClick(v : View){
        when(v.id){
            R.id.btn_cancel_dialog_complain -> {
                builderNew.dismiss()
            }
            R.id.btn_ok_dialog_complain -> {
                val complainMSG = dialogView.findViewById<EditText>(R.id.edt_dialog_content_complain).text
                toast(complainMSG)
                builderNew.dismiss()
            }
        }
    }

    //통신
   fun network(){
        txt_deadline_product.text = "D-3"
        txt_area_tag_product.text = "서울 전체"
        txt_age_tag_product.text = "24~30 개월"
        txt_category_tag_product.text = "카테고리 전체"

        txt_account_name_product.text = "초희"
        rab_product.rating = 4f
        txt_content_product.text = "한두 번 밖에 안입힌 귀여운 모자와 상하의 세트 나눔합니다. 멸균으로 깨끗하게 세탁한거라 바로" +
                "입으셔도 되세요 ㅎㅎ 좋은 주인을 찾길 바랍니다. 많은 분들 신청해주세요!" +
                "\n\n\n"

   }
}
