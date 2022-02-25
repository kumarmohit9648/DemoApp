package com.software96.demoapp.utils

import android.util.Log
import com.software96.demoapp.BuildConfig

object Logger {

    private const val TAG = "DemoApp"

    private fun isDebug() = BuildConfig.DEBUG

    fun log(message: String?) {
        d(message)
    }

    fun d(message: String?) {
        if (isDebug()) Log.d(TAG, "" + message)
    }

    fun d(tag: String?, message: String?) {
        if (isDebug()) Log.d(tag, "" + message)
    }

    fun e(message: String?) {
        if (isDebug()) Log.e(TAG, "" + message)
    }

    fun e(tag: String?, message: String?) {
        if (isDebug()) Log.e(tag, "" + message)
    }
}