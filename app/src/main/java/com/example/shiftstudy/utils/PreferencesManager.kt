package com.example.shiftstudy.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val PREFS_NAME = "shiftstudy_preferences"
    private const val KEY_AUDIO_ENABLED = "audio_enabled"
    private const val KEY_AUDIO_VOLUME = "audio_volume"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Save audio enabled state
     */
    fun setAudioEnabled(context: Context, enabled: Boolean) {
        getPreferences(context).edit().putBoolean(KEY_AUDIO_ENABLED, enabled).apply()
    }

    /**
     * Get audio enabled state
     */
    fun isAudioEnabled(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_AUDIO_ENABLED, false)
    }

    /**
     * Save audio volume (0.0 to 1.0)
     */
    fun setAudioVolume(context: Context, volume: Float) {
        getPreferences(context).edit().putFloat(KEY_AUDIO_VOLUME, volume).apply()
    }

    /**
     * Get audio volume (default 0.5)
     */
    fun getAudioVolume(context: Context): Float {
        return getPreferences(context).getFloat(KEY_AUDIO_VOLUME, 0.5f)
    }
}