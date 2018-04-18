package com.android.nvtrong.wifichuakotlin.presenter

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import com.android.nvtrong.wifichuakotlin.model.WifiStation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*

/**
 * Created by nvtrong on 4/18/2018.
 */
class InteractorMainImpl : InteractorMain {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val datareference: DatabaseReference = database.getReference("wifi")
    lateinit var callback: InteractorMain.OnSubmitFinishMain

    constructor() {
        datareference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d("onChildChanged", p0.toString())
//                callback.onSuccess("ERROR NETWORK!")
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                Log.d("onChildChanged", p0.toString())
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                Log.d("onChildAdded", p0.toString())
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }
        })
    }

    override fun submit(ssid: String, password: String, callback: InteractorMain.OnSubmitFinishMain) {
        this.callback = callback
        if (TextUtils.isEmpty(ssid)) {
            callback.onErrorSSID("SSID ERROR!")
            return
        }
        if (TextUtils.isEmpty(password)) {
            callback.onErrorPassword("PASSWORD ERROR!")
            return
        }
        writeNewWifi(ssid, password)
    }

    fun writeNewWifi(ssid: String, password: String) {
        val wifi = WifiStation()
        wifi.ssid = ssid
        wifi.password = password
        val data = datareference.push().setValue(wifi)
        data.addOnFailureListener { exception -> callback.onSuccess(exception.toString()) }
        data.addOnCompleteListener(OnCompleteListener { callback.onSuccess("Saved completed!") })
    }
}