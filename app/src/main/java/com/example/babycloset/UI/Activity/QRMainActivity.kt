package com.example.babycloset.UI.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.babycloset.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_qrmain.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class QRMainActivity : AppCompatActivity() {
    lateinit var qrscan: IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrmain)

        //큐알 생성
        btn_qr_create.setOnClickListener{
            startActivity<QRListActivity>("id" to 1)
        }
        view_qr_create.setOnClickListener{
            startActivity<QRListActivity>("id" to 1)
        }

        //큐알 스캔
        btn_qr_scan.setOnClickListener{
            qrscan()
        }
        view_qr_scan.setOnClickListener{
            qrscan()
        }
    }
    private fun qrscan(){
        qrscan=IntentIntegrator(this)
        qrscan.setOrientationLocked(false)
        qrscan.setPrompt("인식이 잘 안된다면 코드가 있는\n 휴대폰의 화면 밝기를 높혀 주세요.")
        qrscan.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result: IntentResult =IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result!=null){
            if(result.contents==null){
                toast("Cancelled")
            }else{
                toast("Scanned: "+result.contents)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
