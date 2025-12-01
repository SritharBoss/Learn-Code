package com.srithar.cscmoi.data.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object PreferenceManager {
    private const val PREF_NAME = "app_settings"
    private const val KEY_BASE_URL = "base_url"
    private const val KEY_IS_TRANSLATED = "is_translated"

    private fun prefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getBaseUrl(context: Context): String {
        return prefs(context).getString(KEY_BASE_URL, "http://192.168.1.109:3001/")!!
    }

    fun getIsTranslated(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_IS_TRANSLATED, false)
    }

    fun setIsTranslated(context: Context, value: Boolean) {
        prefs(context).edit { putBoolean(KEY_IS_TRANSLATED, value) }
    }

    fun setBaseUrl(context: Context, url: String) {
        var result = url
        if(!result.endsWith("/")){
            result="$url/"
        }

        prefs(context).edit { putString(KEY_BASE_URL, result) }
    }

    fun getSecurePrefs(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "secure_prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveCredentials(context: Context, email: String, password: String) {
        val prefs = getSecurePrefs(context)
        prefs.edit { putString("email", email).putString("password", password) }
    }

    fun validateOfflineLogin(context: Context, email: String, password: String): Boolean {
        val prefs = getSecurePrefs(context)
        return prefs.getString("email", "") == email && prefs.getString("password", "") == password
    }
}