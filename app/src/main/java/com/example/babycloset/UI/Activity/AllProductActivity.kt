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
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.AllProductRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_all_product.*
import kotlinx.android.synthetic.main.toolbar_all_product.*
import org.jetbrains.anko.startActivityForResult
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

    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_product)

        configToolBar()
        configRecyclerView()

        btn_more_all_product.setOnClickListener {
            getAllPostResponse(pagination)
            pagination++
        }
    }

    fun configRecyclerView(){
        var RVDataList : ArrayList<AllPostRVData> = ArrayList()
        allProductRecyclerViewAdapter = AllProductRecyclerViewAdapter(this, RVDataList)
        rv_item_all_product.adapter = allProductRecyclerViewAdapter
        rv_item_all_product.layoutManager = GridLayoutManager(this, 2)
        getAllPostResponse(1)
        pagination++
    }

    fun getAllPostResponse(pagination : Int){
        val getAllPostResponse = networkService.getAllPostResponse(token, pagination)
        getAllPostResponse.enqueue(object : Callback<GetAllPostResponse>{
            override fun onFailure(call: Call<GetAllPostResponse>, t: Throwable) {
                Log.e("모든 상품 조회 실패", t.toString())
            }

            override fun onResponse(call: Call<GetAllPostResponse>, response: Response<GetAllPostResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        val tmp : ArrayList<AllPostRVData> = response.body()!!.data.allPost
                        allProductRecyclerViewAdapter.RVDataList = tmp
                        allProductRecyclerViewAdapter.notifyDataSetChanged()
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
                rv_filter_all_product.adapter = categoryRecyclerViewAdapter
                rv_filter_all_product.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

                rv_filter_all_product.visibility = View.VISIBLE
            }
        }
    }
}
