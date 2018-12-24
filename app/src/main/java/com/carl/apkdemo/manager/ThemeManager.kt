package com.carl.apkdemo.manager

import android.app.Activity
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
        when (theme) {
            ThemeEnum.Day -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            ThemeEnum.Night -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            ThemeEnum.Auto -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
            }
            ThemeEnum.Custom -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            else -> {}
        }

        app.sharedPreferences.edit()
            .putString(SP_KEY_THEME, theme.value)
            .apply()
    }

    fun switchTo(context: AppCompatActivity, theme: ThemeEnum) {

        val mode = when (theme) {
            ThemeEnum.Day -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeEnum.Night -> AppCompatDelegate.MODE_NIGHT_YES
            ThemeEnum.Auto -> AppCompatDelegate.MODE_NIGHT_AUTO
            ThemeEnum.Custom -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_AUTO
        }

        context.delegate.setLocalNightMode(mode)

//        app.sharedPreferences.edit()
//            .putString(SP_KEY_THEME, theme.value)
//            .apply()
    }
}

enum class ThemeEnum(val value: String) {
    Auto("auto"),
    Day("day"),
    Night("night"),
    Custom("custom");

    fun getThemeByValue(string: String) : ThemeEnum {
        return when (string) {
            Day.value -> Day
            Night.value -> Night
            Custom.value -> Custom
            Auto.value -> Auto
            else -> Auto
        }
    }
}