package com.carl.apkdemo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_switch.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SwitchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch)

        setUp()

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().removeAllStickyEvents()
        EventBus.getDefault().unregister(this)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageManager.getAttachBaseContext(newBase))
    }

    private fun setUp() {
        when (LanguageManager.manager.current()) {
            LanguageEnum.Auto ->        rbGroup.check(R.id.rbAuto)
            LanguageEnum.Chinese ->     rbGroup.check(R.id.rbChinese)
            LanguageEnum.English ->     rbGroup.check(R.id.rbEnglish)
            LanguageEnum.Japanese ->    rbGroup.check(R.id.rbJapanese)
            else ->                     rbGroup.clearCheck()
        }

        rbGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbChinese ->   LanguageManager.manager.switchTo(LanguageEnum.Chinese)
                R.id.rbEnglish ->   LanguageManager.manager.switchTo(LanguageEnum.English)
                R.id.rbAuto ->      LanguageManager.manager.switchTo(LanguageEnum.Auto)
                R.id.rbJapanese ->  LanguageManager.manager.switchTo(LanguageEnum.Japanese)
                else ->             LanguageManager.manager.switchTo(LanguageEnum.Auto)
            }

            EventBus.getDefault().post(MessageEvent(1))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onReceiveEvent(msg: MessageEvent) {
        if (1 == msg.type) {
            LanguageManager.reload(this)
        }
    }
}
