package com.carl.apkdemo

import android.app.Application
import android.content.SharedPreferences

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
    }
}