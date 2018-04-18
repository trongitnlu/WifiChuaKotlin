package com.android.nvtrong.wifichuakotlin.presenter

import android.net.wifi.WifiManager


/**
 * Created by nvtrong on 4/17/2018.
 */
class DetailPresenter(val detailView: DetailView, val interactorConnect: InteractorConnect) : InteractorConnect.onConnectFinishListener {
    override fun onSuccess(s: String) {
        detailView.display(s)
    }

    override fun onFailed() {
        detailView.displayFailed()
    }

    fun connect(ssid: String, key: String, wifi: WifiManager) {
        interactorConnect?.connect(ssid, key, wifi, this)
    }
}


