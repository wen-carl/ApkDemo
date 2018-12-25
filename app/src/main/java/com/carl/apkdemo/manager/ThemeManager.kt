package com.carl.apkdemo.manager

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.carl.apkdemo.App

class ThemeManager private constructor(context: Context) {

    companion object {

        const val SP_KEY_THEME: String = "sp_key_theme"

        val manager: ThemeManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ThemeManager(App.Context)
        }
    }

    private val app: App = context.applicationContext as App

    fun currentTheme() : ThemeEnum {
        val theme = app.sharedPreferences.getString(SP_KEY_THEME, ThemeEnum.Auto.value)?.capitalize()
        return ThemeEnum.valueOf(theme!!)
    }

    fun switchTo(theme: ThemeEnum) {
        AppCompatDelegate.setDefaultNightMode(theme.mode)
        app.sharedPreferences.edit()
            .putString(SP_KEY_THEME, theme.value)
            .apply()
    }

    fun switchTo(context: AppCompatActivity, theme: ThemeEnum) {
        context.delegate.setLocalNightMode(theme.mode)
        app.sharedPreferences.edit()
            .putString(SP_KEY_THEME, theme.value)
            .apply()
    }
}

enum class ThemeEnum(val value: String, val mode: Int) {
    Auto("auto", AppCompatDelegate.MODE_NIGHT_AUTO),
    Day("day", AppCompatDelegate.MODE_NIGHT_NO),
    Night("night", AppCompatDelegate.MODE_NIGHT_YES),
    Custom("custom", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
}