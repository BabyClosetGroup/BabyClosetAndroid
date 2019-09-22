package com.example.babycloset.UI.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.babycloset.Data.CategoryData
import com.example.babycloset.R
import com.example.babycloset.UI.Adapter.CategoryRecyclerViewAdapter
import com.example.babycloset.UI.Adapter.DeadLineProductRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_dead_line_product.*
import kotlinx.android.synthetic.main.toolbar_all_product.*
import org.jetbrains.anko.startActivityForResult


class DeadLineProductActivity : AppCompatActivity() {

    val REQUEST_CODE_CATEGORY = 1100
    lateinit var deadLineProductRecyclerViewAdapter: DeadLineProductRecyclerViewAdapter
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
    var pagination : Int = 1
    var areaList = arrayListOf<String>()
    var ageList = arrayListOf<String>()
    var categoryList = arrayListOf<String>()

    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjozLCJuaWNrbmFtZSI6IuuwlOuCmOuCmO2CpSIsImlhdCI6MTU2ODIxNzE4MiwiZXhwIjoxNTc5MDE3MTgyLCJpc3MiOiJiYWJ5Q2xvc2V0In0.7TL84zswMGWBmPFOVMUddb30FW3CVvir6cyvDPiBX60"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dead_line_product)

        txt_title_all_product.text = "마감임박"
        //configToolBar()
       // configRecyclerView()

        btn_more_deadline_product.setOnClickListener {
            //getAllPostResponse(pagination)
            pagination++
        }
    }


}
