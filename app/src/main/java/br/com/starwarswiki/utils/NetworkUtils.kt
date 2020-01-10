package br.com.starwarswiki.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi

object NetworkUtils {
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            isOnlineAndroidM(connectivityManager)
        else
            isOnline(connectivityManager)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnlineAndroidM(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val netInfo = connectivityManager.getNetworkInfo(network)
        return netInfo != null && netInfo.isAvailable && netInfo.isConnected
    }

    private fun isOnline(connectivityManager: ConnectivityManager): Boolean {
        val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return (wifi != null && wifi.isAvailable && wifi.isConnected) ||
                (mobile != null && mobile.isAvailable && mobile.isConnected)
    }
}