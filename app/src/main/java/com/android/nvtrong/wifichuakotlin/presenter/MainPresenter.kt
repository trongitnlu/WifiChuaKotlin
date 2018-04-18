package com.android.nvtrong.wifichuakotlin.presenter

import android.os.Handler


/**
 * Created by nvtrong on 4/18/2018.
 */
class MainPresenter(val mainView: MainView, val interactor: InteractorMain) : InteractorMain.OnSubmitFinishMain {
    override fun onErrorSSID(error: String) {
        mainView.display(error)
    }

    override fun onErrorPassword(error: String) {
        mainView.display(error)
    }

    override fun onSuccess(success: String) {
        mainView.display(success)
    }

    fun submit(ssid: String, password: String) {
        Handler().postDelayed(Runnable {
            interactor.submit(ssid, password, this)
        }, 2000)

    }
}