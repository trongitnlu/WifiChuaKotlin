package com.android.nvtrong.wifichuakotlin.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.android.nvtrong.wifichuakotlin.R
import com.android.nvtrong.wifichuakotlin.model.WifiStation
import kotlinx.android.synthetic.main.list_item_wifi.view.*

/**
 * Created by nvtrong on 4/17/2018.
 */
class WifiListAdapter(context: Context?) : ArrayAdapter<WifiStation>(context, 0) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    @SuppressLint("StringFormatMatches")
    //@Suppress("IfThenToElvis")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position)
        val view = if (convertView != null) convertView else inflater.inflate(R.layout.list_item_wifi, parent, false)
        view.txt_ssid.text = item.ssid
        view.txt_bssid.text = item.bssid
        view.txt_frequency.text = context.getString(R.string.station_frequency, item.level)
        view.txt_level.text = context.getString(R.string.station_level, item.level)
        return view
    }
}

