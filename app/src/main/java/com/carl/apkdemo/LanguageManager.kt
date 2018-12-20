package com.carl.apkdemo

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.support.v7.app.AppCompatActivity
import java.util.*
import android.content.Intent



class LanguageManager private constructor(context: Context){

    companion object {

        const val SP_KEY_LANGUAGE: String = "sp_key_language"

        val manager: LanguageManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LanguageManager(App.Context)
        }

        fun reload(activity: AppCompatActivity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                activity.recreate()
            } else {
                val intent = Intent(activity, activity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity.startActivity(intent)
            }
        }

        fun getAttachBaseContext(context: Context?) : Context? {
            return if (null == context) {
                null
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    updateLanguageAfterN(context)
                } else {
                    updateLanguageBeforeN(context)
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateLanguageAfterN(context: Context) : Context {
            val locale = manager.currentLocale()
            val config = context.resources.configuration
            config.setLocale(locale)

            return context.createConfigurationContext(config)
        }

        private fun updateLanguageBeforeN(context: Context) : Context {
            val resources = context.resources
            val configuration = resources.configuration
            val locale = manager.currentLocale()
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)

            return context
        }
    }

    private val app: App = context.applicationContext as App

    fun system() : LanguageEnum {
       return current(Locale.getDefault().language)
    }

    fun current() : LanguageEnum {
        val language = app.sharedPreferences.getString(SP_KEY_LANGUAGE, LanguageEnum.Auto.value)
        return if (language.isNullOrEmpty()) {
            system()
        } else {
            current(language)
        }
    }

    fun currentLocale() : Locale {
        val language = app.sharedPreferences.getString(SP_KEY_LANGUAGE, LanguageEnum.Auto.value)
        return if (language.isNullOrEmpty()) {
            Locale.getDefault()
        } else {
            when (language) {
                LanguageEnum.Auto.value ->      Locale.getDefault()
                LanguageEnum.Chinese.value ->   Locale.SIMPLIFIED_CHINESE
                LanguageEnum.English.value ->   Locale.ENGLISH
                LanguageEnum.Japanese.value ->  Locale.JAPANESE
                else ->                         Locale.getDefault()
            }
        }
    }

    fun currentLanguage() : String {
        return currentLocale().language
    }

    private fun current(language: String) : LanguageEnum {
        return when (language) {
            LanguageEnum.Auto.value ->      LanguageEnum.Auto
            LanguageEnum.Chinese.value ->   LanguageEnum.Chinese
            LanguageEnum.English.value ->   LanguageEnum.English
            LanguageEnum.Japanese.value ->  LanguageEnum.Japanese
            else ->                         LanguageEnum.Auto
        }
    }

    fun switchTo(languageEnum: LanguageEnum) {

        val locale = when (languageEnum) {
            LanguageEnum.Auto ->        Locale.getDefault()
            LanguageEnum.Chinese ->     Locale.SIMPLIFIED_CHINESE
            LanguageEnum.English ->     Locale.ENGLISH
            LanguageEnum.Japanese ->    Locale.JAPANESE
            else ->                     Locale.getDefault()
        }

        val config = app.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            config.setLayoutDirection(locale)
        } else {
            config.setLocale(locale)
            config.setLayoutDirection(locale)
            app.resources.updateConfiguration(config, app.resources.displayMetrics)
        }
        app.sharedPreferences.edit()
                .putString(SP_KEY_LANGUAGE, languageEnum.value)
                .apply()
    }
}

enum class LanguageEnum(val value: String) {
    Auto("auto"),
    Chinese("zh"),
    English("en"),
    Japanese("ja")
}