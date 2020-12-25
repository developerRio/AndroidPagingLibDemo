package com.originalstocks.paginglibdemo.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.originalstocks.paginglibdemo.activity.MainActivity


fun isNetworkAvailable(): Boolean {
    var isConnected = false // Initial Value
    val connectivityManager = MainActivity.instanceMain.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}