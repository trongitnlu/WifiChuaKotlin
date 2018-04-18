package com.android.nvtrong.wifichuakotlin.presenter

import android.net.wifi.WifiManager

/**
 * Created by nvtrong on 4/17/2018.
 */
interface InteractorConnect {
    interface onConnectFinishListener {
        fun onSuccess(s: String)
        fun onFailed()
    }

    fun connect(ssid: String, key: String, wifi: WifiManager, onConnectFinishListener: onConnectFinishListener)
}