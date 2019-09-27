package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.Data.DeadLinePostRVData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetDeadLinePostResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostDeadLinePostFilterResponse
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.DeadLineProductRecyclerViewAdapter
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_deadline_product.*
import kotlinx.android.synthetic.main.toolbar_all_product.*
import kotlinx.android.synthetic.main.toolbar_write_post.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeadLineProductActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    val REQUEST_CODE_CATEGORY = 1100
    lateinit var deadLineProductRecyclerViewAdapter: DeadLineProductRecyclerViewAdapter
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
    var pagination : Int = 1
    var fpagination : Int = 1
    var areaList = arrayListOf<String>()
    var ageList = arrayListOf<String>()
    var categoryList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deadline_product)

        txt_title_all_product.text = "마감임박"
        configToolBar()
        configRecyclerView()

        btn_more_deadline_product.setOnClickListener {
            if(rv_filter_deadline_product.visibility == View.VISIBLE){
                postDeadLinePostFilterResponse()
            }else{
                getDeadLinePostResponse()
            }
        }

        btn_letter_toolbar_all_product.setOnClickListener {
            startActivity<EmailActivity>()
        }

    }

    fun configToolBar(){
        btn_filter_toolbar_all_product.setOnClickListener {
            startActivityForResult<CategoryActivity>(REQUEST_CODE_CATEGORY,"requestCode" to REQUEST_CODE_CATEGORY)
        }
    }

    fun configRecyclerView(){
        var datalist : ArrayList<DeadLinePostRVData> = ArrayList()
        deadLineProductRecyclerViewAdapter = DeadLineProductRecyclerViewAdapter(this, datalist)
        rv_item_deadline_product.adapter = deadLineProductRecyclerViewAdapter
        rv_item_deadline_product.layoutManager = GridLayoutManager(this, 2)
        getDeadLinePostResponse()
    }

    //일반 조회
    fun getDeadLinePostResponse(){
        val getDeadLinePostResponse = networkService.getDeadLinePostResponse(SharedPreference.getUserToken(this), pagination)

        getDeadLinePostResponse.enqueue(object : Callback<GetDeadLinePostResponse>{
            override fun onFailure(call: Call<GetDeadLinePostResponse>, t: Throwable) {
                Log.e("마감임박 상품 조회 실패", t.toString())
            }

            override fun onResponse(call: Call<GetDeadLinePostResponse>, response: Response<GetDeadLinePostResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        Log.e("마감임박  상품 조회 성공", pagination.toString())

                        var isNewMessage = response.body()!!.data.isNewMessage

                        if(isNewMessage == 1){ //새메시지가 왔을 경우 이미지 change
                            btn_letter_toolbar_all_product.setImageResource(R.drawable.btn_letter_alarm)
                        }else if(isNewMessage == 0){
                            btn_letter_toolbar_all_product.setImageResource(R.drawable.home_btn_email_update)
                        }

                        if(response.body()!!.data.deadlinePost.isNotEmpty()){
                            val tmp : ArrayList<DeadLinePostRVData> = response.body()!!.data.deadlinePost
                            for(i in 0..tmp.size-1){

                                deadLineProductRecyclerViewAdapter.datalist.add(tmp[i])
                            }
                            deadLineProductRecyclerViewAdapter.notifyDataSetChanged()
                            pagination++
                        }else{
                            toast("게시물이 없습니다.")
                        }
                    }
                }
            }
        })
    }
    //필터
    fun postDeadLinePostFilterResponse(){
        val areaC_rb = WritePostActivity.listToString(areaList)
        val ageC_rb = WritePostActivity.listToString(ageList)
        val catC_rb = WritePostActivity.listToString(categoryList)


        var jsonObject = JSONObject()
        jsonObject.put("area", areaC_rb)
        jsonObject.put("age", ageC_rb)
        jsonObject.put("cloth", catC_rb)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject


        val postDeadLinePostFilterResponse = networkService.postDeadLinePostFilterResponse("application/json",
            SharedPreference.getUserToken(this),fpagination, gsonObject)

        postDeadLinePostFilterResponse.enqueue(object : Callback<PostDeadLinePostFilterResponse>{
            override fun onFailure(call: Call<PostDeadLinePostFilterResponse>, t: Throwable) {
                Log.e("마감임박 상품 필터 조회 실패", t.toString())
            }

            override fun onResponse(call: Call<PostDeadLinePostFilterResponse>, response: Response<PostDeadLinePostFilterResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        Log.e("모든 상품 필터 조회 성공", fpagination.toString())

                        var isNewMessage = response.body()!!.data.isNewMessage

                        if(isNewMessage == 1){ //새메시지가 왔을 경우 이미지 change
                            btn_letter_toolbar_all_product.setImageResource(R.drawable.btn_letter_alarm)
                        }else if(isNewMessage == 0){
                            btn_letter_toolbar_all_product.setImageResource(R.drawable.home_btn_email_update)
                        }

                        if(response.body()!!.data.filteredDeadlinePost.isNotEmpty()){
                            rl_not_filter_post_deadline_product.visibility = View.GONE
                            val tmp : ArrayList<DeadLinePostRVData> = response.body()!!.data.filteredDeadlinePost
                            if(fpagination == 1){
                                deadLineProductRecyclerViewAdapter.datalist.clear()
                                deadLineProductRecyclerViewAdapter.notifyDataSetChanged()
                            }
                            for(i in 0..response.body()!!.data.filteredDeadlinePost.size-1){
                                deadLineProductRecyclerViewAdapter.datalist.add(tmp[i])
                            }
                            deadLineProductRecyclerViewAdapter.notifyDataSetChanged()
                            fpagination++
                        }else{
                            deadLineProductRecyclerViewAdapter.notifyDataSetChanged()
                            rl_not_filter_post_deadline_product.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //필터
        if (requestCode == REQUEST_CODE_CATEGORY) {
            if (resultCode == Activity.RESULT_OK) {
                areaList = data!!.getStringArrayListExtra("areaList")
                ageList = data.getStringArrayListExtra("ageList")
                categoryList = data.getStringArrayListExtra("categoryList")

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
                rv_filter_deadline_product.adapter = categoryRecyclerViewAdapter
                rv_filter_deadline_product.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

                rv_filter_deadline_product.visibility = View.VISIBLE

                txt_title_all_product.text = "필터적용"

                postDeadLinePostFilterResponse()
            }
        }
    }

}
