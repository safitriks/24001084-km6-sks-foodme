package com.example.foodme.data.source.local.pref

import android.content.Context
import com.example.foodme.utils.SharedPreferenceUtils
import com.example.foodme.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun isUsingDarkMode(): Boolean
    fun setUsingDarkMode(isUsingDarkMode: Boolean)
}

class UserPreferenceImpl(private val context: Context) : UserPreference {

    private val pref = SharedPreferenceUtils.createPreference(context, PREF_NAME)

    override fun isUsingDarkMode(): Boolean = pref.getBoolean(KEY_IS_USING_DARK_MODE, false)

    override fun setUsingDarkMode(isUsingDarkMode: Boolean) {
        pref[KEY_IS_USING_DARK_MODE] = isUsingDarkMode
    }

    companion object {
        const val PREF_NAME = "kokomputer-pref"
        const val KEY_IS_USING_DARK_MODE = "KEY_IS_USING_DARK_MODE"
    }
}