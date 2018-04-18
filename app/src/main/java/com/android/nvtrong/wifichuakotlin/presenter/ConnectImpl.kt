package com.android.nvtrong.wifichuakotlin.presenter

import android.net.wifi.WifiManager
import android.os.Handler
import com.android.nvtrong.wifichuakotlin.model.WifiStation
import com.google.firebase.database.*
import android.net.wifi.WifiConfiguration
import android.util.Log


/**
 * Created by nvtrong on 4/17/2018.
 */
class ConnectImpl() : InteractorConnect {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val datareference: DatabaseReference = database.getReference("wifi")
    var isConnect: Boolean = false

    override fun connect(ssid: String, key: String, wifi: WifiManager, onConnectFinishListener: InteractorConnect.onConnectFinishListener) {
        datareference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var wifiStation: WifiStation = p0.getValue(WifiStation::class.java)!!
                Log.d("FIRE_BASE", wifiStation.toString())
                if (ssid.equals(wifiStation.ssid, true)) {
                    connectWifi(wifiStation.ssid!!, wifiStation.password!!, wifi, onConnectFinishListener)
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

        });
        Handler().postDelayed({
            if (!isConnect) {
                onConnectFinishListener.onFailed()
            }
        }, 10000)

    }

    fun connectWifi(ssid: String, password: String, wifi: WifiManager, callback: InteractorConnect.onConnectFinishListener) {
        Log.d("CONNECT...", ssid + "--" + password)
        val config = WifiConfiguration()
        config.SSID = "\"" + ssid + "\""
        config.preSharedKey = "\"" + password + "\""
        val netId = wifi.addNetwork(config)
        wifi.saveConfiguration()
        wifi.disconnect()
        wifi.enableNetwork(netId, true)
        wifi.reconnect()
        Log.d("DDDDDDDDDDDDDDDDDDDDD", wifi.connectionInfo.ssid + "--" + ssid)
        val ssidConnected = wifi.connectionInfo.ssid.substring(1, wifi.connectionInfo.ssid.length - 1)
        if (ssidConnected.equals(ssid, true)) {
            Handler().postDelayed(Runnable { callback.onSuccess("Connected Success!") }, 2000)
            isConnect = true
            return
        }
    }
}