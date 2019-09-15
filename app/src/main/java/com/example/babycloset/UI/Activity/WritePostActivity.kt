package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_write_post.*
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import okhttp3.MultipartBody
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.io.File


class WritePostActivity : AppCompatActivity() {
    var deadline : String = ""
    lateinit var pictureUri : Uri
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
    val REQUEST_CODE_CATEGORY : Int = 1000
    val REQUEST_CODE_PICTURE1 : Int = 100
    val REQUEST_CODE_PICTURE2 : Int = 200
    val REQUEST_CODE_PICTURE3 : Int = 300
    val REQUEST_CODE_PICTURE4 : Int = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        //이미지 선택
        img_write_post1.setOnClickListener { showImageDialog(1) }
        img_write_post2.setOnClickListener { showImageDialog(2) }
        img_write_post3.setOnClickListener { showImageDialog(3) }
        img_write_post4.setOnClickListener { showImageDialog(4) }

        //카테고리 선택 액티비티 이동
        btn_category_write_post.setOnClickListener {
            startActivityForResult<CategoryActivity>(REQUEST_CODE_CATEGORY,"requestCode" to REQUEST_CODE_CATEGORY)
        }

        //다이얼로그 팝업
        btn_deadline_write_post.setOnClickListener { showDeadlineDialog() }

        //검사(카테고리 선택, 마감기한 선택) -> 통신 -> (딜레이후) 상품보기 액티비티 이동
        btn_share_write_post.setOnClickListener {
           isValid()
        }
    }


    //툴바

    //마감기간 선택 다이얼로그
    fun showDeadlineDialog(){
        val deadlineList = arrayOf<CharSequence>("5일","4일", "3일", "2일", "1일")
        val builder = AlertDialog.Builder(this)
        builder.setItems(deadlineList, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0-> setDialogTag(deadlineList, 0)
                1-> setDialogTag(deadlineList, 1)
                2-> setDialogTag(deadlineList, 2)
                3-> setDialogTag(deadlineList, 3)
                4-> setDialogTag(deadlineList, 4)

            }
            dialog.dismiss()
        })
        builder.show()
    }

    //마감기한 태그 설정
    fun setDialogTag(deadlineList : Array<CharSequence>, num :Int){
        deadline = deadlineList[num].toString()
        txt_deadline_tag.text = deadline
        txt_deadline_tag.visibility = View.VISIBLE
    }

    //이미지 다이얼로그
    fun showImageDialog(requestCodeNumber: Int){
        val option = arrayOf<CharSequence>("이미지 선택하기","삭제하기")
        val builder = AlertDialog.Builder(this)
        builder.setItems(option, DialogInterface.OnClickListener { dialog, which ->
            when(which){
                0->{
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                    intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    when(requestCodeNumber){
                        1-> startActivityForResult(intent,REQUEST_CODE_PICTURE1)
                        2-> startActivityForResult(intent,REQUEST_CODE_PICTURE2)
                        3-> startActivityForResult(intent,REQUEST_CODE_PICTURE3)
                        4-> startActivityForResult(intent,REQUEST_CODE_PICTURE4)
                    }
                }
                1->{
                    when(requestCodeNumber){
                        1-> img_write_post1.setImageBitmap(null)
                        2-> img_write_post2.setImageBitmap(null)
                        3-> img_write_post3.setImageBitmap(null)
                        4-> img_write_post4.setImageBitmap(null)
                    }
                }
            }
            dialog.dismiss()
        })
        builder.show()
    }


    //갤러리에서 선택한 이미지 보여주기(썸네일)
   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //카테고리
        if(requestCode == REQUEST_CODE_CATEGORY){
            if(resultCode == Activity.RESULT_OK) {

                val areaList = data!!.getStringArrayListExtra("areaList")
                val ageList = data!!.getStringArrayListExtra("ageList")
                val categoryList = data!!.getStringArrayListExtra("categoryList")

                var dataList : ArrayList<CategoryData> = ArrayList()

                for(i in 0..areaList.size-1){
                    dataList.add(CategoryData(areaList[i]))
                }
                for(i in 0..ageList.size-1){
                    dataList.add(CategoryData(ageList[i]))
                }
                for(i in 0..categoryList.size-1){
                    dataList.add(CategoryData(categoryList[i]))
                }

                categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(this, dataList)
                rv_category_write_post.adapter = categoryRecyclerViewAdapter
                rv_category_write_post.layoutManager = LinearLayoutManager(this,LinearLayout.HORIZONTAL, false)

                rv_category_write_post.visibility = View.VISIBLE
            }
        }


        //이미지
        if(requestCode == REQUEST_CODE_PICTURE1){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri = it.data
                    Glide.with(this).load(pictureUri).into(img_write_post1)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE2){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri = it.data
                    Glide.with(this).load(pictureUri).into(img_write_post2)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE3){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri = it.data
                    Glide.with(this).load(pictureUri).thumbnail(0.1f).into(img_write_post3)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE4){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri = it.data
                    Glide.with(this).load(pictureUri).into(img_write_post4)

                }
            }
        }
    }


    fun isValid(){
        if(rv_category_write_post.visibility == View.GONE){
            showNoticeDialog(this,"카테고리를 선택해주세요!\n", "카테고리를 선택해야", "글을 작성할 수 있습니다.")
        }
        else if(deadline==""){
            showNoticeDialog(this,"마감기한을 선택해주세요!\n","마감기한을 선택해야","글을 작성할 수 있습니다." )
        }
        else if(edt_title_write_post.text.toString()==""){
            showNoticeDialog(this, "제목을 입력해주세요!\n","제목을 입력하셔야","글을 작성할 수 있습니다." )
        }else{
            toast("통신")
        }
    }

    //알림 팝업
    companion object{
        fun showNoticeDialog(ctx: Context,title : String, msg1 : String, msg2 : String){
            val builder = AlertDialog.Builder(ctx)
            builder
                .setTitle(title)
                .setMessage(Html.fromHtml("<font color='#767676'>$msg1<br>$msg2</font>"))
                .setNegativeButton(Html.fromHtml("<font color='#ffc107'>확인</font>"), DialogInterface.OnClickListener { dialog, which ->  })
            builder.show()
        }
    }

    //통신
}
