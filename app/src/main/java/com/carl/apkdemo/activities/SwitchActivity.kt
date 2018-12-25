package com.carl.apkdemo.activities

import android.os.Bundle
import android.view.MenuItem
import com.carl.apkdemo.R
import com.carl.apkdemo.base.BaseActivity
import com.carl.apkdemo.manager.LanguageEnum
import com.carl.apkdemo.manager.LanguageManager
import com.carl.apkdemo.manager.ThemeEnum
import com.carl.apkdemo.manager.ThemeManager
import com.carl.apkdemo.models.MessageEvent
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
            LanguageEnum.Auto -> rbGroup.check(R.id.rbAuto)
            LanguageEnum.Chinese -> rbGroup.check(R.id.rbChinese)
            LanguageEnum.English -> rbGroup.check(R.id.rbEnglish)
            LanguageEnum.Japanese -> rbGroup.check(R.id.rbJapanese)
            else -> rbGroup.clearCheck()
        }

        rbGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbChinese -> LanguageManager.manager.switchTo(LanguageEnum.Chinese)
                R.id.rbEnglish -> LanguageManager.manager.switchTo(LanguageEnum.English)
                R.id.rbAuto -> LanguageManager.manager.switchTo(LanguageEnum.Auto)
                R.id.rbJapanese -> LanguageManager.manager.switchTo(LanguageEnum.Japanese)
                else -> LanguageManager.manager.switchTo(LanguageEnum.Auto)
            }

            EventBus.getDefault().post(MessageEvent(1))
        }

        when (ThemeManager.manager.currentTheme()) {
            ThemeEnum.Auto -> rbThemeGroup.check(R.id.rbThemeAuto)
            ThemeEnum.Day -> rbThemeGroup.check(R.id.rbDay)
            ThemeEnum.Night -> rbThemeGroup.check(R.id.rbNight)
            ThemeEnum.Custom -> rbThemeGroup.check(R.id.rbCustom)
            else -> rbThemeGroup.clearCheck()
        }

        rbThemeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbThemeAuto -> ThemeManager.manager.switchTo(ThemeEnum.Auto)
                R.id.rbDay -> ThemeManager.manager.switchTo(ThemeEnum.Day)
                R.id.rbNight -> ThemeManager.manager.switchTo(ThemeEnum.Night)
                R.id.rbCustom -> ThemeManager.manager.switchTo(ThemeEnum.Custom)
                else -> ThemeManager.manager.switchTo(ThemeEnum.Auto)
            }

            EventBus.getDefault().post(MessageEvent(2))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
