package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.babycloset.DB.SharedPreference
import com.example.babycloset.Data.SearchProductData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostSearchResponse
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.SearchProductRecyclerAdapter
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    lateinit var searchProductRecyclerAdapter: SearchProductRecyclerAdapter
    val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"
    var searchDataList: ArrayList<SearchProductData> = ArrayList()
    var search_word=""
    var page=1;

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val intent= intent
        search_word=intent.getStringExtra("search_word")
        edit_searh_after_value.text=Editable.Factory.getInstance().newEditable(search_word)

        //재검색
        research()
        //리사이클뷰
        configRecyclerView()
        //통신
        postSearchResponse()
        //상품 더보기 입력했을 때
        btn_search_product_more.setOnClickListener {
            page++;
            this.postSearchResponse()
        }
    }
    private fun research(){
        edit_searh_after_value.setOnKeyListener { v, keyCode, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                page=1
                searchDataList.clear()
                search_word=edit_searh_after_value.text.toString()
                this.postSearchResponse()

                //키보드 내리기
                imm.hideSoftInputFromWindow(currentFocus?.windowToken,InputMethodManager.SHOW_FORCED)
                edit_searh_after_value.showSoftInputOnFocus=true

                return@setOnKeyListener true
            }
            false
        }
    }

    private fun configRecyclerView(){
        searchProductRecyclerAdapter= SearchProductRecyclerAdapter(this,searchDataList)
        rv_item_search_all.adapter=searchProductRecyclerAdapter
        rv_item_search_all.layoutManager=GridLayoutManager(this,2)
    }

    private fun postSearchResponse(){
        var jsonObject= JSONObject()
        jsonObject.put("query",search_word)
        val gsonObject= JsonParser().parse(jsonObject.toString()) as JsonObject

        val token: String = SharedPreference.getUserToken(this)
        val postSearchResponse=networkService.postSearchResponse("application/json",token,page,gsonObject)
        postSearchResponse.enqueue(object: Callback<PostSearchResponse>{
            override fun onFailure(call: Call<PostSearchResponse>, t: Throwable) {
                Log.e("search fail",t.toString())
            }

            override fun onResponse(call: Call<PostSearchResponse>, response: Response<PostSearchResponse>) {
                if(response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        val tmp=response.body()!!.data.allPost
                        if(tmp.size == 0){
                            if(page == 1) {
                                frame_search.visibility = View.INVISIBLE
                                frame_search_none.visibility = View.VISIBLE
                            }
                        }else{
                            frame_search_none.visibility=View.INVISIBLE
                            frame_search.visibility= View.VISIBLE
                            searchProductRecyclerAdapter.dataList.addAll(tmp)
                            searchProductRecyclerAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

}
