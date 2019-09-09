package com.example.babycloset.UI.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.babycloset.R
import kotlinx.android.synthetic.main.activity_qrmain.*
import org.jetbrains.anko.startActivity

class QRMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrmain)

        btn_qr_scan.setOnClickListener {
            startActivity<QRScanActivity>("id" to 1)
        }
    }
}
