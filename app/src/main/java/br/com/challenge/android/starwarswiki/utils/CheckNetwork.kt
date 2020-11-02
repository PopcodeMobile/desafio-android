package br.com.challenge.android.starwarswiki.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.widget.Toast
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

// Reference:
// https://gist.github.com/PasanBhanu/730a32a9eeb180ec2950c172d54bb06a

class CheckNetwork {

    companion object {
        var isNetworkConnected = false
        const val ERROR_INTERNET_NOT_AVAILABLE = "Error: Internet connection not available"
    }

    fun checkIfDeviceIsReadyToConnectInternet(context: Context) {
        // at first checking using the old approach
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            verifyInternetEnabledAndConnecting(context)
        } else {
            registerNetworkCallback(context)
        }

        if(!isNetworkConnected) {
            Toast
                .makeText(
                    context,
                    "Please make sure the device has an internet connection.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun verifyInternetEnabledAndConnecting(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivityManager.activeNetworkInfo != null) {
            try {
                val urlc = URL("http://clients3.google.com/generate_204")
                    .openConnection() as HttpURLConnection

                urlc.setRequestProperty("User-Agent", "Android")
                urlc.setRequestProperty("Connection", "close")

                urlc.connectTimeout = 1500

                urlc.connect()

                isNetworkConnected = urlc.responseCode == 204 && urlc.contentLength == 0
            } catch (e: IOException) {
                isNetworkConnected = false

                e.printStackTrace()
            }

            return
        }

        isNetworkConnected = false
    }

    private fun registerNetworkCallback(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val connectivityManagerNetworkCallback = object : ConnectivityManager.NetworkCallback() {
                val activeNetworks: MutableList<Network> = mutableListOf()

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    // Add to list of active networks if not already in list
                    if (activeNetworks
                            .none {activeNetwork ->
                                activeNetwork.networkHandle == network.networkHandle
                            }) {
                        activeNetworks.add(network)
                    }

                    isNetworkConnected = activeNetworks.isNotEmpty()
                }

                override fun onLost(network: Network) {
                    super.onLost(network)

                    // Remove network from active network list
                    activeNetworks.removeAll {activeNetwork ->
                        activeNetwork.networkHandle == network.networkHandle
                    }

                    isNetworkConnected = activeNetworks.isNotEmpty()
                }
            }

            connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), connectivityManagerNetworkCallback)
        }

    }

}