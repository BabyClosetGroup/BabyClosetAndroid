package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_write_post.*
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostWritePostResponse
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivityForResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class WritePostActivity : AppCompatActivity() {
    var deadline : String = ""
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    var areaList = arrayListOf<String>()
    var ageList = arrayListOf<String>()
    var categoryList = arrayListOf<String>()

    var pictureUri1 : Uri? = null
    var pictureUri2 : Uri? = null
    var pictureUri3 : Uri? = null
    var pictureUri4 : Uri? = null
    var pictureList =  ArrayList<MultipartBody.Part>()


    val REQUEST_CODE_CATEGORY : Int = 1000
    val REQUEST_CODE_PICTURE1 : Int = 100
    val REQUEST_CODE_PICTURE2 : Int = 200
    val REQUEST_CODE_PICTURE3 : Int = 300
    val REQUEST_CODE_PICTURE4 : Int = 400

    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        //이미지 선택
        img_write_post1.setOnClickListener { showImageDialog(1) }
        img_write_post2.setOnClickListener { showImageDialog(2) }
        img_write_post3.setOnClickListener { showImageDialog(3) }
        img_write_post4.setOnClickListener { showImageDialog(4) }

        //카테고리 선택 액티비티 이동
        rl_category_write_post.setOnClickListener {
            startActivityForResult<CategoryActivity>(REQUEST_CODE_CATEGORY,"requestCode" to REQUEST_CODE_CATEGORY)
        }

        //다이얼로그 팝업
        rl_deadline_write_post.setOnClickListener { showDeadlineDialog() }

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

                areaList = data!!.getStringArrayListExtra("areaList")
                ageList = data!!.getStringArrayListExtra("ageList")
                categoryList = data!!.getStringArrayListExtra("categoryList")

                val dataList : ArrayList<CategoryData> = ArrayList()

                for(i in 0 .. areaList.size-1){
                    dataList.add(CategoryData(areaList[i]))
                }
                for(i in 0 .. ageList.size-1){
                    dataList.add(CategoryData(ageList[i]))
                }
                for(i in 0 .. categoryList.size-1){
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
                    pictureUri1 = it.data!!
                    Glide.with(this).load(pictureUri1).into(img_write_post1)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE2){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri2 = it.data!!
                    Glide.with(this).load(pictureUri2).into(img_write_post2)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE3){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri3 = it.data!!
                    Glide.with(this).load(pictureUri3).thumbnail(0.1f).into(img_write_post3)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE4){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri4 = it.data!!
                    Glide.with(this).load(pictureUri4).into(img_write_post4)

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
            postWritePostResponse()
        }
    }

    //통신
    fun postWritePostResponse(){

        val title_rb = stringToRequestBody(edt_title_write_post.text.toString())
        val content_rb = stringToRequestBody(edt_contents_wirte_post.text.toString())
        val deadline_rb = stringToRequestBody(deadline)
        val areaC_rb = stringToRequestBody(listToString(areaList))
        val ageC_rb = stringToRequestBody(listToString(ageList))
        val catC_rb = stringToRequestBody(listToString(categoryList))

        imagesToMBP(pictureUri1!!, pictureUri2!!, pictureUri3!!, pictureUri4!!, pictureList, contentResolver)

        val postWritePostResponse = networkService.postWritePostResponse(token, title_rb, content_rb, deadline_rb, areaC_rb, ageC_rb,catC_rb, pictureList)

        postWritePostResponse.enqueue(object : Callback<PostWritePostResponse>{
            override fun onFailure(call: Call<PostWritePostResponse>, t: Throwable) {
                Log.e("게시물 등록 액티비티 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<PostWritePostResponse>, response: Response<PostWritePostResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        Log.e("성공", response.message())
                    }
                    Log.e("10알", response.message())
                }

            }
        })
    }

    //ModifyPostActivity 활용
    companion object{
        //알림 팝업
        fun showNoticeDialog(ctx: Context,title : String, msg1 : String, msg2 : String){
            val builder = AlertDialog.Builder(ctx)
            builder
                .setTitle(title)
                .setMessage(Html.fromHtml("<font color='#767676'>$msg1<br>$msg2</font>"))
                .setNegativeButton(Html.fromHtml("<font color='#ffc107'>확인</font>"), DialogInterface.OnClickListener { dialog, which ->  })
            builder.show()
        }

        //카테고리->string
        fun listToString(list: ArrayList<String>) : String{
            var categoryStr = ""
            for(i in 0 ..list.size-1){
                if(i == list.size-1){
                    categoryStr += list[i]
                }else{
                    categoryStr += list[i] + ","
                }
            }
            return categoryStr
        }

        //String -> RequestBody
        fun stringToRequestBody(str : String):RequestBody{
            val rb =  RequestBody.create(MediaType.parse("text/plain"), str)
            return rb
        }

        //uri -> RequestBody
        fun imageToRequestBody(uri: Uri, contentResolver: ContentResolver) : RequestBody{
            val options = BitmapFactory.Options()
            val inputStream: InputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
            val photoBody = RequestBody.create(MediaType.parse("image/jpg"),byteArrayOutputStream.toByteArray())

            return photoBody
        }

        //images requestBody -> ArrayList<MultipartBody.Part>
        fun imagesToMBP(uri1: Uri, uri2: Uri, uri3: Uri, uri4: Uri, pictureList :  ArrayList<MultipartBody.Part>, contentResolver: ContentResolver){
            val uriList = arrayListOf(uri1, uri2, uri3, uri4)
            for(i in 0 .. uriList.size-1){
                if(uriList[i] == null){
                    Log.e("uri", "is null")
                }
                else{
                    //리퀘스트 바디 만들기
                    val picture_rb = MultipartBody.Part.createFormData("postImages", File(uriList[i].toString()).name, imageToRequestBody(uriList[i]!!, contentResolver))
                    pictureList.add(picture_rb)
                }
            }
        }
    }
}
