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
import com.example.babycloset.Data.AllPostRVData
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.Get.GetAllPostResponse
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostAllPostFilterResponse
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.AllProductRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_all_product.*
import kotlinx.android.synthetic.main.toolbar_all_product.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AllProductActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    val REQUEST_CODE_CATEGORY = 1100
    lateinit var allProductRecyclerViewAdapter: AllProductRecyclerViewAdapter
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
    var pagination : Int = 1
    var fpagination : Int = 1
    var areaList = arrayListOf<String>()
    var ageList = arrayListOf<String>()
    var categoryList = arrayListOf<String>()

    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_product)

        configToolBar()
        configRecyclerView()

        btn_more_all_product.setOnClickListener {
            if(rv_filter_all_product.visibility == View.VISIBLE){
                toast("filter")
                getAllPostFilterResponse()
            }else{
                getAllPostResponse()
            }
        }




    }

    fun configRecyclerView(){
        var RVDataList : ArrayList<AllPostRVData> = ArrayList()
        allProductRecyclerViewAdapter = AllProductRecyclerViewAdapter(this, RVDataList)
        rv_item_all_product.adapter = allProductRecyclerViewAdapter
        rv_item_all_product.layoutManager = GridLayoutManager(this, 2)
        getAllPostResponse()
    }

    fun getAllPostResponse(){
        val getAllPostResponse = networkService.getAllPostResponse(token, pagination)
        getAllPostResponse.enqueue(object : Callback<GetAllPostResponse>{
            override fun onFailure(call: Call<GetAllPostResponse>, t: Throwable) {
                Log.e("모든 상품 조회 실패", t.toString())
            }

            override fun onResponse(call: Call<GetAllPostResponse>, response: Response<GetAllPostResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        Log.e("모든 상품 조회 성공", pagination.toString())
                        if(response.body()!!.data.allPost.isNotEmpty()){
                            val tmp : ArrayList<AllPostRVData> = response.body()!!.data.allPost
                            for(i in 0..tmp.size-1){
                                allProductRecyclerViewAdapter.RVDataList.add(tmp[i])
                            }
                            allProductRecyclerViewAdapter.notifyDataSetChanged()
                            pagination++
                        }else{
                            toast("게시물이 없습니다.")
                           
                        }
                    }
                }
            }
        })
    }

    fun getAllPostFilterResponse(){
        val areaC_rb = WritePostActivity.listToString(areaList)
        val ageC_rb = WritePostActivity.listToString(ageList)
        val catC_rb = WritePostActivity.listToString(categoryList)


        var jsonObject = JSONObject()
        jsonObject.put("area", areaC_rb)
        jsonObject.put("age", ageC_rb)
        jsonObject.put("cloth", catC_rb)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val getAllPostFilterResponse = networkService.postAllPostFilterResponse("application/json", token,fpagination, gsonObject)
        getAllPostFilterResponse.enqueue(object : Callback<PostAllPostFilterResponse>{
            override fun onFailure(call: Call<PostAllPostFilterResponse>, t: Throwable) {
                Log.e("모든 상품 필터 조회 실패", t.toString())
            }

            override fun onResponse(call: Call<PostAllPostFilterResponse>, response: Response<PostAllPostFilterResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        Log.e("모든 상품 필터 조회 성공", fpagination.toString())


                        if(response.body()!!.data.filteredAllPost.isNotEmpty()){
                            val tmp : ArrayList<AllPostRVData> = response.body()!!.data.filteredAllPost
                           // allProductRecyclerViewAdapter.RVDataList.clear()
                            for(i in 0..response.body()!!.data.filteredAllPost.size-1){
                                allProductRecyclerViewAdapter.RVDataList.add(tmp[i])
                            }
                            allProductRecyclerViewAdapter.notifyDataSetChanged()
                            fpagination++
                        }else{
                            allProductRecyclerViewAdapter.notifyDataSetChanged()
                            toast("조건에 맞는 게시물이 없습니다.")

                        }


                    }
                }
            }
        })
    }
    fun configToolBar(){
        btn_filter_toolbar_all_product.setOnClickListener {
            startActivityForResult<CategoryActivity>(REQUEST_CODE_CATEGORY,"requestCode" to REQUEST_CODE_CATEGORY)
        }
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
                rv_filter_all_product.adapter = categoryRecyclerViewAdapter
                rv_filter_all_product.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

                rv_filter_all_product.visibility = View.VISIBLE

                getAllPostFilterResponse()
                toast("filter")
            }
        }
    }
}
