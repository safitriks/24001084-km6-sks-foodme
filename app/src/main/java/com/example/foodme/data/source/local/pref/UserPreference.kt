package com.example.foodme.data.source.local.pref

import android.content.SharedPreferences
import com.example.foodme.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun isUsingGridMode(): Boolean

    fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserPreferenceImpl(private val pref: SharedPreferences) : UserPreference {
    override fun isUsingGridMode(): Boolean = pref.getBoolean(KEY_IS_USING_GRID_MODE, false)

    override fun setUsingGridMode(isUsingGridMode: Boolean) {
        pref[KEY_IS_USING_GRID_MODE] = isUsingGridMode
    }

    companion object {
        const val PREF_NAME = "foodme-pref"
        const val KEY_IS_USING_GRID_MODE = "KEY_IS_USING_GRID_MODE"
    }
}
