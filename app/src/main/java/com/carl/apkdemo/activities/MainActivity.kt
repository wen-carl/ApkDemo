package com.carl.apkdemo.activities

import android.content.Intent
import android.os.Bundle
import com.carl.apkdemo.base.BaseActivity
import com.carl.apkdemo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addContentView(R.layout.activity_main)

        textView.setOnClickListener {
            startActivity(Intent(this, SwitchActivity::class.java))
        }
    }
}
