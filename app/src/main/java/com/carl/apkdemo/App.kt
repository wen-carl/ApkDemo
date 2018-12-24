package com.carl.apkdemo

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.carl.apkdemo.manager.LanguageEnum
import com.carl.apkdemo.manager.LanguageManager
import com.carl.apkdemo.manager.ThemeEnum
import com.carl.apkdemo.manager.ThemeManager

class App : Application() {

    companion object {
        lateinit var Context: Application
    }

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        Context = this
        sharedPreferences = getSharedPreferences(packageName, android.content.Context.MODE_PRIVATE)

        val language = sharedPreferences.getString(LanguageManager.SP_KEY_LANGUAGE, "")
        if (language.isNullOrEmpty()) {
            sharedPreferences.edit()
                .putString(LanguageManager.SP_KEY_LANGUAGE, LanguageEnum.Auto.value)
                .apply()
        }

        val theme = sharedPreferences.getString(ThemeManager.SP_KEY_THEME, "")
        if (theme.isNullOrEmpty()) {
            if (language.isNullOrEmpty()) {
                sharedPreferences.edit()
                    .putString(ThemeManager.SP_KEY_THEME, ThemeEnum.Auto.value)
                    .apply()
            }
        } else {
            ThemeManager.manager.switchTo(ThemeManager.manager.currentTheme())
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}