package com.android.nvtrong.wifichuakotlin.fragment

import android.Manifest
import android.app.Fragment
import android.app.ProgressDialog
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.nvtrong.wifichuakotlin.R
import com.android.nvtrong.wifichuakotlin.WifiActivity
import com.android.nvtrong.wifichuakotlin.model.WifiStation
import com.android.nvtrong.wifichuakotlin.presenter.ConnectImpl
import com.android.nvtrong.wifichuakotlin.presenter.DetailPresenter
import com.android.nvtrong.wifichuakotlin.presenter.DetailView
import com.android.nvtrong.wifichuakotlin.presenter.InteractorConnect
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_wifi_detail.*
import org.reactivestreams.Subscription

/**
 * Created by nvtrong on 4/17/2018.
 */
class WifiDetailFragment() : Fragment(), DetailView {
    var progressDialog: ProgressDialog? = null
    var presenter: DetailPresenter? = null
    var interactorConnect: InteractorConnect? = null
    var wifiStation: WifiStation? = null
    private val wifiManager: WifiManager get() = activity.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun showProgess() {
        progressDialog = ProgressDialog.show(activity, null, "Loading")
    }

    override fun hidenProgess() {
        progressDialog?.dismiss()
    }

    override fun display(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
        hidenProgess()
    }

    override fun displayFailed() {
        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
        hidenProgess()
    }

    companion object {
        private const val ARG_STATION = "station"
        val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION: Int = 100

        fun newInstance(station: WifiStation): WifiDetailFragment {
            val f = WifiDetailFragment()
            f.arguments = Bundle()
            f.arguments.putParcelable(ARG_STATION, station)

            return f
        }
    }

    private var contentTextView: TextView? = null

    private var strings: Observable<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        interactorConnect = ConnectImpl()
        presenter = DetailPresenter(this, interactorConnect as ConnectImpl)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_wifi_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_content.setText(R.string.prompt_loading)
        wifiStation = arguments.getParcelable<WifiStation>(ARG_STATION)
        if (wifiStation != null) {
            txt_content.setText(arguments.getParcelable<WifiStation>(ARG_STATION).ssid)
            btn_connect.setOnClickListener(View.OnClickListener { connect(wifiStation?.ssid.toString(), wifiStation?.bssid.toString(), wifiManager) })
        }
        Log.d("DDDDDDD", arguments.getParcelable<WifiStation>(ARG_STATION).toString())
        val subscription = strings?.subscribe { text -> contentTextView?.text = text }

    }

    fun connect(ssid: String, bssid: String, wifi: WifiManager) {
        showProgess()
        presenter?.connect(ssid, bssid, wifi)
    }

//    private fun checkPermissions(): Boolean {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                && activity.checkSelfPermission(Manifest.permission.INTERNET) !== android.content.pm.PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(
//                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
//                    PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION)
//            return false
//        } else {
//            return true
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION -> {
//                connect(wifiStation?.ssid!!, wifiStation?.bssid!!)
//            }
//        }
//    }
}