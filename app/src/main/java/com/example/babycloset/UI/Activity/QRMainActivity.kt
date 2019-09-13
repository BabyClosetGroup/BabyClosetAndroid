package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_qrmain.*
import org.jetbrains.anko.startActivity

class QRMainActivity : AppCompatActivity() {

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
            startActivity<QRScanActivity>("id" to 2)
        }
        view_qr_scan.setOnClickListener{
            startActivity<QRScanActivity>("id" to 2)
        }
    }
}
