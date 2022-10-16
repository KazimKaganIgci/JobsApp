package com.kazim.jobsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Constants {

    const val BASE_URL ="https://remotive.io/api/"
    const val TAG="Error Retrofit API"
    fun CheckInternetConnection(context: Context):Boolean{
        val connectivityManager=context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network=connectivityManager.activeNetwork?:return false
        val activityNetwork=connectivityManager.getNetworkCapabilities(network)?:return false
        return when{
            activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
            activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
            activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
            else ->false
        }
    }
}