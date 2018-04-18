package com.android.nvtrong.wifichuakotlin

import android.Manifest
import android.app.Fragment
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.nvtrong.wifichuakotlin.fragment.WifiDetailFragment
import com.android.nvtrong.wifichuakotlin.fragment.WifiListFragment
import com.android.nvtrong.wifichuakotlin.model.WifiStation

class WifiActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 120
    }

    private val wifiManager: WifiManager get() = getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var listFragment: WifiListFragment? = null
    private var detailFragment: WifiDetailFragment? = null

    private val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val result = wifiManager.scanResults
            if (listFragmentVisible && result != null) {
                listFragment?.updateItems(result)
            }
        }

    }
    private var listFragmentVisible: Boolean = false
    private var wifiReceiverRegistered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)
        setTitle(R.string.title_wifi)
        transitionToList()
        // Enable Wi-Fi
        if (wifiManager.isWifiEnabled == false) {
            Toast.makeText(this, R.string.prompt_enabling_wifi, Toast.LENGTH_SHORT).show()
            wifiManager.isWifiEnabled = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.wifi, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                refreshList()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        startScanning()
    }

    fun onResumeFragment(fragment: WifiListFragment) {
        listFragmentVisible = true
        if (fragment == listFragment) {
            listFragmentVisible = true

            refreshList()
        }
    }

    override fun onStop() {
        stopScanning()
        super.onStop()
    }

    private fun stopScanning() {
        if (wifiReceiverRegistered) {
            unregisterReceiver(wifiReceiver)
            wifiReceiverRegistered = false
        }
    }

    private fun startScanning() {
        if (checkPermissions()) {
            registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
            wifiReceiverRegistered = true
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun checkPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION)
            return false
        } else {
            return true
        }
    }

    /**
     * Transitions
     */

    private fun transition(fragment: Fragment, add: Boolean = false) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.layout_frame, fragment)
        if (add) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun transitionToList() {
        listFragment = WifiListFragment.newInstance()
        transition(listFragment!!)
    }

    fun transitionToDetail(item: WifiStation) {
        detailFragment = WifiDetailFragment.newInstance(item)
        transition(detailFragment!!, true)
    }

    /**
     * List data
     */

    private fun refreshList() {
        listFragment?.clearItems()
        wifiManager.startScan()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION -> {
                startScanning()
            }
        }
    }
}
