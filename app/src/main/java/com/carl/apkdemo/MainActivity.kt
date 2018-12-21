package com.carl.apkdemo

import android.content.Intent
import android.os.Bundle
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
