package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.util.Log
import com.example.babycloset.Data.SearchProductData
import com.example.babycloset.Network.ApplicationController
import com.example.babycloset.Network.NetworkService
import com.example.babycloset.Network.Post.PostSearchResponse
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.SearchProductRecyclerAdapter
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_search.*
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


    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val intent= intent
        search_word=intent.getStringExtra("search_word")
        edit_searh_after_value.text=Editable.Factory.getInstance().newEditable(search_word)

        configRecyclerView()
        postSearchResponse()
    }
    private fun configRecyclerView(){
//        var area=ArrayList<String>()
//        area.add("동작구")
//        searchDataList.add(SearchProductData(0,"gkg","d-1","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg",area))
//        searchDataList.add(SearchProductData(0,"gkg","d-1","https://sopt24server.s3.ap-northeast-2.amazonaws.com/1567341981635.jpeg",area))

        searchProductRecyclerAdapter= SearchProductRecyclerAdapter(this,searchDataList)
        rv_item_search_all.adapter=searchProductRecyclerAdapter
        rv_item_search_all.layoutManager=GridLayoutManager(this,2)
    }
    private fun postSearchResponse(){
        var jsonObject= JSONObject()
        jsonObject.put("query",search_word)
        val gsonObject= JsonParser().parse(jsonObject.toString()) as JsonObject

        val postSearchResponse=networkService.postSearchResponse("application/json",token,1,gsonObject)
        postSearchResponse.enqueue(object: Callback<PostSearchResponse>{
            override fun onFailure(call: Call<PostSearchResponse>, t: Throwable) {
                Log.e("search fail",t.toString())
            }

            override fun onResponse(call: Call<PostSearchResponse>, response: Response<PostSearchResponse>) {
                if(response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        searchDataList.clear()
                        val tmp=response.body()!!.data.allPost

                        searchProductRecyclerAdapter.dataList.addAll(tmp)
                        searchProductRecyclerAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

}
