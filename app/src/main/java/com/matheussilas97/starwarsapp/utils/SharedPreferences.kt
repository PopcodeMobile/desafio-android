package com.matheussilas97.starwarsapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log

class SharedPreferences {
    private val prefs: android.content.SharedPreferences
    private val TAG = "SharedPreferences"
    private val editor: android.content.SharedPreferences.Editor
    private val filename = "preferences"
    var isLoggingEnabled = false
        private set

    val all: Map<String, *>
        get() {
            if (isLoggingEnabled)
                Log.d(TAG, "Total of " + prefs.all.size + " values stored")
            return prefs.all
        }

    @SuppressLint("CommitPrefEdits")
    private constructor(context: Context) {
        prefs = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    @SuppressLint("CommitPrefEdits")
    private constructor(context: Context, filename: String) {
        prefs = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun setLogging(enabled: Boolean) {
        isLoggingEnabled = enabled
    }

    fun clearAll() {
        editor.clear()
        editor.apply()
    }

    operator fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    fun removeValue(key: String) {
        if (isLoggingEnabled)
            Log.d(TAG, "Removing key $key from preferences")
        editor.remove(key)
        editor.apply()
    }

    /*
        Retrieving methods
     */

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        if (isLoggingEnabled)
            Log.d(TAG, "Value: " + key + " is " + prefs.getBoolean(key, defaultValue))
        return prefs.getBoolean(key, defaultValue)
    }

    fun getInteger(key: String, defaultValue: Int): Int {
        if (isLoggingEnabled)
            Log.d(TAG, "Value: " + key + " is " + prefs.getInt(key, defaultValue))
        return prefs.getInt(key, defaultValue)
    }

    fun getString(key: String, defaultValue: String?): String? {
        if (isLoggingEnabled)
            Log.d(
                TAG,
                "Value: " + key + " is " + if (defaultValue != null) prefs.getString(
                    key,
                    defaultValue
                )!!.trim { it <= ' ' } else null)
        return if (defaultValue != null) prefs.getString(key, defaultValue)!!.trim { it <= ' ' } else null
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        if (isLoggingEnabled)
            Log.d(TAG, "Value: " + key + " is " + prefs.getFloat(key, defaultValue))
        return prefs.getFloat(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        if (isLoggingEnabled)
            Log.d(TAG, "Value: " + key + " is " + prefs.getLong(key, defaultValue))
        return prefs.getLong(key, defaultValue)
    }

    fun getDouble(key: String, defaultValue: Double): Double {
        if (isLoggingEnabled)
            Log.d(
                TAG,
                "Value: " + key + " is " + java.lang.Double.longBitsToDouble(
                    prefs.getLong(
                        key,
                        java.lang.Double.doubleToLongBits(defaultValue)
                    )
                )
            )
        return java.lang.Double.longBitsToDouble(prefs.getLong(key, java.lang.Double.doubleToLongBits(defaultValue)))
    }

    fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? {
        if (isLoggingEnabled)
            Log.d(TAG, "Value: " + key + " is " + prefs.getStringSet(key, defaultValue)!!.toString())
        return prefs.getStringSet(key, defaultValue)
    }

    /*
        Saving methods
    */

    fun saveBoolean(key: String, value: Boolean) {
        if (isLoggingEnabled)
            Log.d(TAG, "Saving $key with value $value")
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun saveInteger(key: String, value: Int) {
        if (isLoggingEnabled)
            Log.d(TAG, "Saving $key with value $value")
        editor.putInt(key, value)
        editor.apply()
    }

    fun saveString(key: String, value: String?) {
        if (isLoggingEnabled)
            Log.d(TAG, "Saving $key with value $value")
        editor.putString(key, value?.trim { it <= ' ' })
        editor.apply()
    }

    fun saveFloat(key: String, value: Float) {
        if (isLoggingEnabled)
            Log.d(TAG, "Saving $key with value $value")
        editor.putFloat(key, value)
        editor.apply()
    }

    fun saveLong(key: String, value: Long) {
        if (isLoggingEnabled)
            Log.d(TAG, "Saving $key with value $value")
        editor.putLong(key, value)
        editor.apply()
    }

    fun saveDouble(key: String, value: Double) {
        if (isLoggingEnabled)
            Log.d(TAG, "Saving $key with value $value")
        editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        editor.apply()
    }

    fun saveStringSet(key: String, set: Set<String>) {
        if (isLoggingEnabled)
            Log.d(TAG, "Saving " + key + " with value " + set.toString())
        editor.putStringSet(key, set)
        editor.apply()
    }

    companion object {

        private var mInstance: SharedPreferences? = null

        fun getInstance(context: Context): SharedPreferences {
            if (mInstance == null)
                mInstance = SharedPreferences(context)
            return mInstance as SharedPreferences
        }

        fun getInstance(context: Context, filename: String): SharedPreferences {
            if (mInstance != null && mInstance!!.filename != filename || mInstance == null)
                mInstance = SharedPreferences(context, filename)
            return mInstance as SharedPreferences
        }
    }
}