package com.example.planetcleanarchitecture.ui_layer.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkConnection @Inject constructor(@ApplicationContext val context: Context) : LiveData<Boolean>() {

    private val connectionManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var networkConnectionCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateNetworkConnection()

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectionManager.registerDefaultNetworkCallback(connectivityManagerCallback())
            }

            else -> {
                @Suppress("DEPRECATION")
                context.registerReceiver(
                    networkReceiver,
                    IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                )
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectionManager.unregisterNetworkCallback(connectivityManagerCallback())
    }

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        networkConnectionCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }
        }
        return networkConnectionCallback
    }

    private fun updateNetworkConnection() {

        val nw: Network = connectionManager.activeNetwork!!
        val activeNetworkConnection: NetworkCapabilities? =
            connectionManager.getNetworkCapabilities(nw)
        if (activeNetworkConnection != null && (activeNetworkConnection.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            ) || activeNetworkConnection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || activeNetworkConnection.hasTransport(
                NetworkCapabilities.TRANSPORT_ETHERNET
            ) || activeNetworkConnection.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH))
        ) {
            postValue(true)
        } else {
            postValue(false)
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNetworkConnection()
        }

    }

}