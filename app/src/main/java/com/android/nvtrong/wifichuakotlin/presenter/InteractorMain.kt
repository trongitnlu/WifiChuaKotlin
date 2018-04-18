package com.android.nvtrong.wifichuakotlin.presenter

/**
 * Created by nvtrong on 4/18/2018.
 */
interface InteractorMain {
    interface OnSubmitFinishMain {
        fun onErrorSSID(error: String)
        fun onErrorPassword(error: String)
        fun onSuccess(success: String)
    }

    fun submit(ssid: String, password: String, callback: OnSubmitFinishMain)
}