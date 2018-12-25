package com.carl.apkdemo.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.carl.apkdemo.R
import com.carl.apkdemo.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addContentView(R.layout.activity_main)
    }

    override fun onFabClick(view: View) {
        startActivity(Intent(this, SwitchActivity::class.java))
    }
}
