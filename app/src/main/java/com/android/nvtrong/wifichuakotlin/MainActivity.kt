package com.android.nvtrong.wifichuakotlin

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.nvtrong.wifichuakotlin.presenter.InteractorMain
import com.android.nvtrong.wifichuakotlin.presenter.InteractorMainImpl
import com.android.nvtrong.wifichuakotlin.presenter.MainPresenter
import com.android.nvtrong.wifichuakotlin.presenter.MainView
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity() : AppCompatActivity(), MainView {
    lateinit var presenter: MainPresenter
    lateinit var interactor: InteractorMain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        interactor = InteractorMainImpl()
        presenter = MainPresenter(this, interactor)
        btn_scan_wifi.setOnClickListener { v ->
            startActivity(Intent(this, WifiActivity::class.java))
        }
        btn_submit.setOnClickListener(View.OnClickListener {
            showProgress()
            presenter.submit(txt_ssid.text.toString(), txt_password.text.toString())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.prompt_settings_not_implemented, Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    lateinit var progress: ProgressDialog
    override fun showProgress() {
        progress = ProgressDialog.show(this, null, "Loading...")
        progress.show()
    }

    override fun hidePregess() {
        progress.dismiss()
    }

    override fun display(notify: String) {
        hidePregess()
        txt_password.setText("")
        txt_ssid.setText("")
        Toast.makeText(applicationContext, notify, Toast.LENGTH_SHORT).show()

    }
}
