package com.carl.apkdemo

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_switch.*
import org.greenrobot.eventbus.EventBus

class SwitchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceContentViewWith(R.layout.activity_switch)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUp()
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
}
