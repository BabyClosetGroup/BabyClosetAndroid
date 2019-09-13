package com.example.babycloset.UI.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.babycloset.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import org.jetbrains.anko.toast

class QRScanActivity : AppCompatActivity() {

    lateinit var qrscan: IntentIntegrator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)

        qrscan=IntentIntegrator(this)
        qrscan.setOrientationLocked(false)
        qrscan.setPrompt("인식이 잘 안된다면 코드가 있는\n 휴대폰의 화면 밝기를 높혀 주세요.")
        qrscan.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result: IntentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
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
