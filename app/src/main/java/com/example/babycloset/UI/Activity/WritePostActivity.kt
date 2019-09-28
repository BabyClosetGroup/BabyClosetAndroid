package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_write_post.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostWritePostResponse
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import kotlinx.android.synthetic.main.toolbar_product.*
import kotlinx.android.synthetic.main.toolbar_write_post.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class WritePostActivity : AppCompatActivity() {

    var deadline : String = ""
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    var areaList = arrayListOf<String>()
    var ageList = arrayListOf<String>()
    var categoryList = arrayListOf<String>()

    var btnShareState : Boolean = true

    var pictureUri1 : Uri? = null
    var pictureUri2 : Uri? = null
    var pictureUri3 : Uri? = null
    var pictureUri4 : Uri? = null
    var pictureList =  ArrayList<MultipartBody.Part>()

    var postIdx : Int = 0

    val REQUEST_CODE_CATEGORY : Int = 1000
    val REQUEST_CODE_PICTURE1 : Int = 100
    val REQUEST_CODE_PICTURE2 : Int = 200
    val REQUEST_CODE_PICTURE3 : Int = 300
    val REQUEST_CODE_PICTURE4 : Int = 400

    val networkService : NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        checkPermission()

        configWritePost()
    }

    fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("Tag", "권한 설정 완료")
            }  else {
                Log.d("Tag", "권한 설정 요청")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d("Tag", "onRequestPermissionsResult")
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d("Tag", "Permission: " + permissions[0] + "was " + grantResults[0])
        }
    }

    fun configWritePost(){
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
            if(btnShareState){
                isValid()
            }else{

            }
        }

        //쪽지
//        btn_letter_write_post.setOnClickListener {
//            startActivity<EmailActivity>()
//        }


        edt_title_write_post.setOnClickListener {
            edt_title_write_post.requestFocus()
        }
        edt_contents_wirte_post.setOnClickListener {
            edt_contents_wirte_post.requestFocus()
        }

    }

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
                    Log.e("이미지", "가져오기")
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
                        1-> {
                            img_write_post1.setImageBitmap(null)
                            pictureUri1 = null
                        }
                        2-> {
                            img_write_post2.setImageBitmap(null)
                            pictureUri2 = null
                        }
                        3-> {
                            img_write_post3.setImageBitmap(null)
                            pictureUri3 = null
                        }
                        4-> {
                            img_write_post4.setImageBitmap(null)
                            pictureUri4 = null
                        }
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
                    Log.e("1", pictureUri1.toString())
                    Glide.with(this).load(pictureUri1).into(img_write_post1)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE2){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri2 = it.data!!
                    Log.e("2", pictureUri2.toString())
                    Glide.with(this).load(pictureUri2).into(img_write_post2)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE3){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri3 = it.data!!
                    Log.e("3", pictureUri3.toString())
                    Glide.with(this).load(pictureUri3).into(img_write_post3)
                }
            }
        }
        if(requestCode == REQUEST_CODE_PICTURE4){
            if(resultCode == Activity.RESULT_OK){
                data?.let {
                    pictureUri4 = it.data!!
                    Log.e("4", pictureUri4.toString())
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
        }else if(edt_contents_wirte_post.text.toString()==""){
            showNoticeDialog(this, "내용을 작성해주세요!\n", "내용을 작성하셔야", "글을 작성할 수 있습니다.")
        }
        else if(pictureUri1 == null){
           showNoticeDialog(this, "메인 사진을 첨부해주세요!\n","사진을 한장 이상 첨부하셔야","글을 작성할 수 있습니다." )
        }else{
            btnShareState = false
            toast("글 작성을 완료하였습니다!")
            postWritePostResponse()
        }
    }

    //통신
    fun postWritePostResponse(){

        val title_rb = stringToRequestBody(edt_title_write_post.text.toString())
        Log.e("content",edt_contents_wirte_post.text.toString())
        val content_rb = stringToRequestBody(edt_contents_wirte_post.text.toString())
        val deadline_rb = stringToRequestBody(deadline)
        val areaC_rb = stringToRequestBody(listToString(areaList))
        val ageC_rb = stringToRequestBody(listToString(ageList))
        val catC_rb = stringToRequestBody(listToString(categoryList))

        if(pictureUri1 != null)
        {
            val rb = createMBP(contentResolver, pictureUri1!!)
            pictureList.add(rb)
            Log.e("1", "성공")
        }
        if(pictureUri2 != null)
        {
            val rb =createMBP(contentResolver, pictureUri2!! )
            pictureList.add(rb)
            Log.e("2", "성공")
        }
        if(pictureUri3 != null)
        {
            val rb =createMBP(contentResolver, pictureUri3!!)
            pictureList.add(rb)
            Log.e("3", "성공")
        }
        if(pictureUri4 != null)
        {
            val rb =createMBP(contentResolver, pictureUri4!! )
            pictureList.add(rb)
            Log.e("4", "성공")
        }

        val postWritePostResponse = networkService.postWritePostResponse(SharedPreference.getUserToken(this), title_rb,
            content_rb, deadline_rb, areaC_rb, ageC_rb,catC_rb, pictureList )

        postWritePostResponse.enqueue(object : Callback<PostWritePostResponse>{
            override fun onFailure(call: Call<PostWritePostResponse>, t: Throwable) {
                Log.e("게시물 등록 액티비티 통신 실패", t.toString())
            }

            override fun onResponse(call: Call<PostWritePostResponse>, response: Response<PostWritePostResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        postIdx = response.body()!!.data.postIdx
                        Log.e("postIdx in response", postIdx.toString())

                        startActivity<ProductActivity>("postIdx" to postIdx)
                        finish()

                    }
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
                    categoryStr += list[i] + ", "
                }
            }
            return categoryStr
        }

        //String -> RequestBody
        fun stringToRequestBody(str : String):RequestBody{
            val rb =  RequestBody.create(MediaType.parse("text/plain"), str)
            return rb
        }

        fun createMBP(cr : ContentResolver, uri : Uri) : MultipartBody.Part {
            val options = BitmapFactory.Options()
            val inputStream  = cr.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)

            val photoBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())
            val picture_rb = MultipartBody.Part.createFormData("postImages", "img", photoBody)

            return picture_rb
        }

        fun bitmapToFile(ctx : Context, b : Bitmap, fileName : String) : String{

            //임시파일 저장 경로
            val storage : File = ctx.cacheDir
            val name = fileName

            val tempFile = File(storage, name)

            tempFile.createNewFile()
            val fos  = FileOutputStream(tempFile)
            b.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.close()

            return tempFile.absolutePath
        }
    }
}
