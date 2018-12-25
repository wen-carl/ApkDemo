package com.carl.apkdemo.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.carl.apkdemo.R
import com.carl.apkdemo.manager.LanguageEnum
import com.carl.apkdemo.manager.LanguageManager
import com.carl.apkdemo.manager.ThemeEnum
import com.carl.apkdemo.manager.ThemeManager
import com.carl.apkdemo.models.MessageEvent
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
            onFabClick(view)
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

    protected open fun onFabClick(view: View) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    protected open fun onReceiveEvent(msg: MessageEvent) {

        when (msg.type) {
            1 -> onLanguageChanged(LanguageManager.manager.current())
            2 -> onThemeChanged(ThemeManager.manager.currentTheme())
        }
    }

    protected open fun onLanguageChanged(language: LanguageEnum) {
        LanguageManager.reload(this)
    }

    protected open fun onThemeChanged(theme: ThemeEnum) {
        ThemeManager.manager.switchTo(this, theme)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageManager.getAttachBaseContext(newBase))
    }
}
