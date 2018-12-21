package com.carl.apkdemo

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.content_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        EventBus.getDefault().register(this)
    }

    fun addContentView(@LayoutRes layoutResID: Int) {
        layoutInflater.inflate(layoutResID, baseContainer)
    }

    fun replaceContentViewWith(@LayoutRes layoutResID: Int) {
        if (baseContainer.childCount >= 1) {
            baseContainer.removeAllViews()
        }

        layoutInflater.inflate(layoutResID, baseContainer)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().removeAllStickyEvents()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    open fun onReceiveEvent(msg: MessageEvent) {
        if (1 == msg.type) {
            LanguageManager.reload(this)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageManager.getAttachBaseContext(newBase))
    }
}
